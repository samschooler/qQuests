package me.quaz3l.qQuests.Util.Store;

import javax.persistence.PersistenceException;

import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.Chat;

public class PersistenceDatabase {
	public PersistenceDatabase() {
		checkDDL();
	}

	public void set(final String key, String value) {
		// Create a new bean
		PersistenceBean bean = qQuests.plugin.getDatabase().createEntityBean(PersistenceBean.class);
		// Set bean values
		bean.setKey(key);
		bean.setValue(value);
		
		Chat.logger("debug", bean.getKey() + " : " + bean.getValue());
		
		// Save the bean to the database
		qQuests.plugin.getDatabase().save(bean);
	}
	public String get(final String key) {
		// Create a new bean
		return qQuests.plugin.getDatabase()
				.find(PersistenceBean.class)
				.where()
				.ieq("key", key)
				.findUnique()
				.getValue();
	}
	
	public void shutdown() {
		
	}
	
	
	private void checkDDL() {
		try {
			qQuests.plugin.getDatabase().find(PersistenceBean.class).findRowCount();
		} catch(PersistenceException e) {
			installDDL();
		}
		
	}
	protected void installDDL() {
        SpiEbeanServer serv = (SpiEbeanServer) qQuests.plugin.getDatabase();
        DdlGenerator gen = serv.getDdlGenerator();

        gen.runScript(false, gen.generateCreateDdl());
    }
	protected void removeDDL() {
        SpiEbeanServer serv = (SpiEbeanServer) qQuests.plugin.getDatabase();
        DdlGenerator gen = serv.getDdlGenerator();

        gen.runScript(true, gen.generateDropDdl());
    }
}
