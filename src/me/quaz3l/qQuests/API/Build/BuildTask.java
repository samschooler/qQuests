package me.quaz3l.qQuests.API.Build;

import me.quaz3l.qQuests.API.Util.Task;

public class BuildTask {
	public Integer no;
	public String type;
	public Integer id;
	public String name;
	public Integer amount;
	
	public BuildTask(Integer task) {
		this.no = task;
	}
	public Task create() {
		return new Task(this);
	}
}