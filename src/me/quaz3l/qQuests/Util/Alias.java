package me.quaz3l.qQuests.Util;

public class Alias {
	public static String getRealCMD(String alias) {
		String s = Chat.removeColors(alias).toLowerCase();
		if(s.contains("give") || s.contains("start")) {
			return "give";
		} else if(s.contains("tasks") || s.contains("progress")) {
			return "tasks";
		} else if(s.contains("info")) {
			return "info";
		} else if(s.contains("drop")) {
			return "drop";
		} else if(s.contains("done") || s.contains("finish") || s.contains("end")) {
			return "done";
		} else {
			return "";
		}
	}
}
