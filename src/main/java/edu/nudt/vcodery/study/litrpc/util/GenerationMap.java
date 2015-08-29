package edu.nudt.vcodery.study.litrpc.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * This is the simple {@link Map} used for cache. Two generation are split in
 * this map, and the new (used) data will be put into the new generation.
 * Evolution will be processed, by clear old generation and swap these two, when
 * the the size of map hit the heap size.<br/>
 * Since GenerationMap will never be used to hold important data, and
 * concurrency mistakes could be tolerated, so neither of these methods are
 * thread safe.<br/>
 * Tests show that, the success rate will achieve 99.84% while use 100 space
 * to cache 10000 data set, after 1*10^8 times random request.
 * 
 * @author vcodery 2015-07-23
 */
public class GenerationMap<K, V> implements Map<K, V> {

	private int maxSize = 256;

	private int size = 0;

	private Map<K, V> newGeneration = new LinkedHashMap<K, V>();
	private Map<K, V> oldGeneration = new LinkedHashMap<K, V>();

	public GenerationMap() {
	}

	public GenerationMap(int maxSize) {
		if (maxSize > 0) {
			this.maxSize = maxSize;
		}
	}

	public void setMaxSize(int maxSize) {
		if (0 > maxSize) {
			this.maxSize = maxSize;
		}
	}

	@Deprecated
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Deprecated
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return newGeneration.containsKey(key) || oldGeneration.containsKey(key);
	}

	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return newGeneration.containsValue(value) || oldGeneration.containsKey(value);
	}

	@SuppressWarnings("unchecked")
	public V get(Object key) {
		// TODO Auto-generated method stub
		V value = null;
		if (null == (value = newGeneration.get(key))) {

			if (null != (value = oldGeneration.get(key))) {
				// put new used data into newG if it only exists in oldG
				newGeneration.put((K) key, value);
			}
		}
		return value;
	}

	private void checkAndEvolue() {
		if (size >= maxSize) {
			Map<K, V> tmp = newGeneration;
			newGeneration = oldGeneration;
			oldGeneration = tmp;
			newGeneration.clear();

			size = oldGeneration.size();
		}
	}

	public V put(K key, V value) {
		// TODO Auto-generated method stub
		if (!containsKey(value)) {
			newGeneration.put(key, value);
			size++;
		}
		// int tmp = newGeneration.size();
		// size += (newGeneration.size() - tmp);
		// checkAndEvolue();
		return value;
	}

	public V remove(Object key) {
		// TODO Auto-generated method stub
		V valueFromNew = newGeneration.remove(key);
		V valueFromOld = oldGeneration.remove(key);

		if (null == valueFromNew && null == valueFromOld) {
			return null;
		}
		size--;
		return (null == valueFromNew) ? valueFromNew : valueFromOld;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		Iterator<?> it = m.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			Entry<K, V> entry = (Entry<K, V>) it.next();
			if (!containsKey(entry.getKey())) {
				size++;
				newGeneration.put(entry.getKey(), entry.getValue());
				checkAndEvolue();
			}
		}
	}

	public void clear() {
		// TODO Auto-generated method stub
		oldGeneration.clear();
		newGeneration.clear();
		size = 0;
	}

	@Deprecated
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		Set<K> result = new LinkedHashSet<K>();
		result.addAll(newGeneration.keySet());
		result.addAll(oldGeneration.keySet());
		return result;
	}

	@Deprecated
	public Collection<V> values() {
		// TODO Auto-generated method stub
		Set<V> result = new LinkedHashSet<V>();
		result.addAll(newGeneration.values());
		result.addAll(oldGeneration.values());
		return (Collection<V>) result;
	}

	@Deprecated
	public Set<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		Set<Entry<K, V>> result = new LinkedHashSet<Entry<K, V>>();
		result.addAll(newGeneration.entrySet());
		result.addAll(oldGeneration.entrySet());
		return null;
	}
}