package me.quaz3l.qQuests.Util;

import java.util.ArrayList;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;

public class QuestFrag {
	public static String get(String frag) {
		frag = frag.toLowerCase();
		ArrayList<Quest> matches = new ArrayList<Quest>();
		for(Quest q :qQuests.plugin.qAPI.getQuests().values()) {
			// If it is exact, return it
			if(q.name().toLowerCase().contentEquals(frag))
				return q.name();
			if(q.name().toLowerCase().contains(frag))
				matches.add(q);
		}
		if(matches.size() > 0) {
			ArrayList<Integer> scores = new ArrayList<Integer>();
			int largest = 0;
			int index = 0;
			int i = 0;
			for(Quest q: matches) {
				scores.add(q.name().toLowerCase().compareTo(frag));
				i++;
			}
			i=0;
			for (Integer score : scores) {
				if ( score >= largest ) {
					largest = score;
					index = i;
					i++;
				}
			}
			return matches.get(index).name();
		} else return null;
	}
}
