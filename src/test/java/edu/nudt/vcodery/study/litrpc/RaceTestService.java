package edu.nudt.vcodery.study.litrpc;

import java.util.Map;

public interface RaceTestService {
	public Map<String, Object> getMap();

	public String getString();

	public RaceDO getDO();

	public boolean longTimeMethod();

	public Integer throwException() throws Exception;
}