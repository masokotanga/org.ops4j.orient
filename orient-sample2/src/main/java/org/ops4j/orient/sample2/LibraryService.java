/*
 * Copyright 2013 Harald Wellmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops4j.orient.sample2;

import static org.ops4j.orient.sample2.OrientDatabaseConnectionProducer.db;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.ops4j.orient.adapter.api.OrientDatabaseConnection;
import org.ops4j.orient.sample2.model.Author;
import org.ops4j.orient.sample2.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orientechnologies.orient.core.entity.OEntityManager;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;


/**
 * @author Harald Wellmann
 * 
 */
@Stateless
public class LibraryService {

    private static Logger log = LoggerFactory.getLogger(LibraryService.class);

    @Inject
    private OrientDatabaseConnection con;

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void registerEntityClasses() {
        OEntityManager em = db(con).getEntityManager();
        em.registerEntityClass(Author.class);
        em.registerEntityClass(Book.class);
    }

    public void createEntities() {
        Book hobbit = db(con).newInstance(Book.class);
        hobbit.setTitle("The Hobbit");
        con.object().save(hobbit);
    }

    public List<Book> findBooks() {
        log.info("finding books");
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return db(con).query(new OSQLSynchQuery<Book>("select from Book"));
    }

}
