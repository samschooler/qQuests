package me.quaz3l.qQuests.API.Effects;

public class MessageEffect extends qEffect {

	@Override
	public void executeEffect(String player, Object value) {}

	@Override
	public int passedRequirement(String player, Object value) {return 0;}

	@Override
	public int validate(Object value) {return 0;}

	@Override
	public String parseError(String player, Object value, int errorCode) {return null;}

	@Override
	public String getName() {
		return "message";
	}

	@Override
	public void onEnable() {}

	@Override
	public void onDisable() {}

}
