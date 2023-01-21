package ru.otus.migrate.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.migrate.models.AuthorMongo;

import java.util.UUID;

@ChangeLog(order = "0001")
public class DatabaseInit {

    @ChangeSet(order = "0000", id = "dropDB", author = "me", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "0001", id = "initPersons", author = "me", runAlways = true)
    public void addData(MongockTemplate template) {
        template.save(new AuthorMongo(this.getID(), "Nick", "LN", "FN", "MN"));
        template.save(new AuthorMongo(this.getID(), "Nick2", "LN2", "FN2", "MN2"));
        template.save(new AuthorMongo(this.getID(), "Nick3", "LN3", "FN3", "MN3"));
        template.save(new AuthorMongo(this.getID(), "Nick4", "LN4", "FN4", "MN4"));
        template.save(new AuthorMongo(this.getID(), "Nick5", "LN5", "FN5", "MN5"));
    }

    private String getID() {
        return UUID.randomUUID().toString();
    }
}
