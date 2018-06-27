package com.dh;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class TestHashMap {

	@Test
	public void testHash() {
		HashMap<BadHashCode, BadHashCode> mp = new HashMap();
		HashMap<Integer, Integer> stringHashMap = new HashMap();
		int length = 1 << 4;
		IntStream.range(0, length).forEach(i -> {
			BadHashCode code = new BadHashCode(i + "");
			mp.put(code, code);
			//	stringHashMap.put(i,i);
		});

		mp.forEach((k, v) -> {
			System.out.println(k + "---" + v);
		});
		BadHashCode code = new BadHashCode(12 + "");
		System.out.println(mp.get(code));
		System.out.println("exe end");
	}


	@Test
	public void testConcurrentHash() {
		ConcurrentHashMap<Integer, Integer> mp = new ConcurrentHashMap();
		IntStream.range(0, 2).forEach(i -> {
			new Thread(() -> {
				IntStream.range(0, 2).forEach(j -> {
					mp.put(j, j);
				});
			}).start();

		});
	}

	static class BadHashCode {
		String code;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public BadHashCode(String code) {
			this.code = code;
		}

		@Override
		public int hashCode() {
			return 1;
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		@Override
		public String toString() {
			return "BadHashCode{" +
					"code='" + code + '\'' +
					'}';
		}
	}
}
