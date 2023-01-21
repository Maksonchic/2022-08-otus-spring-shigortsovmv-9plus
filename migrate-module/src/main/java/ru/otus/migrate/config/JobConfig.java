package ru.otus.migrate.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.migrate.models.AuthorH2;
import ru.otus.migrate.models.AuthorMongo;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final MongoDatabaseFactory mongoDatabaseFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final int CHUNK_SIZE = 5;

    @Bean
    public MongoItemReader<AuthorMongo> reader() {
        return new MongoItemReaderBuilder<AuthorMongo>()
                .name("authorMongoReader")
                .collection("AUTHORS")
                .query(Query.query(Criteria.where("nickName").exists(true)))
                .sorts(new HashMap<>())
                .targetType(AuthorMongo.class)
                .template(new MongoTemplate(mongoDatabaseFactory))
                .build();
    }

    @Bean
    public ItemProcessor<AuthorMongo, AuthorH2> processor() {
        return authorMongo -> new AuthorH2(0
                , authorMongo.getNickName()
                , authorMongo.getLastName()
                , authorMongo.getFirstName()
                , authorMongo.getMiddleName());
    }

    @Bean
    public JpaItemWriter<AuthorH2> writer() {
        return new JpaItemWriterBuilder<AuthorH2>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step migrate(
            MongoItemReader<AuthorMongo> reader
            , JpaItemWriter<AuthorH2> writer
            , ItemProcessor<AuthorMongo, AuthorH2> processor) {
        return stepBuilderFactory.get("step1")
                .<AuthorMongo, AuthorH2>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job mainJob(Step migrate) {
        return jobBuilderFactory.get("mainJob")
                .incrementer(new RunIdIncrementer())
                .flow(migrate)
                .end()
                .build();
    }
}
