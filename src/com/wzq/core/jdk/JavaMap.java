package com.wzq.core.jdk;

/**
 * @Author:wangzhenqing
 * @Date:2015年01月22日10:59:49
 * @Description:HashMap get和put
 */
public class JavaMap {
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *         (A <tt>null</tt> return can also indicate that the map
     *         previously associated <tt>null</tt> with <tt>key</tt>.)
     */
//    public V put(K key, V value) {
//        if (table == EMPTY_TABLE) {
//            inflateTable(threshold);
//        }
//        if (key == null)
//            return putForNullKey(value);
//        int hash = hash(key);
//        int i = indexFor(hash, table.length);
//        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
//            Object k;
//            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
//                V oldValue = e.value;
//                e.value = value;
//                e.recordAccess(this);
//                return oldValue;
//            }
//        }
//
//        modCount++;
//        addEntry(hash, key, value, i);
//        return null;
//    }

    /**
     * Adds a new entry with the specified key, value and hash code to
     * the specified bucket.  It is the responsibility of this
     * method to resize the table if appropriate.
     *
     * Subclass overrides this to alter the behavior of put method.
     */
//    void addEntry(int hash, K key, V value, int bucketIndex) {
//        if ((size >= threshold) && (null != table[bucketIndex])) {
//            resize(2 * table.length);
//            hash = (null != key) ? hash(key) : 0;
//            bucketIndex = indexFor(hash, table.length);
//        }
//
//        createEntry(hash, key, value, bucketIndex);
//    }

    /**
     * Like addEntry except that this version is used when creating entries
     * as part of Map construction or "pseudo-construction" (cloning,
     * deserialization).  This version needn't worry about resizing the table.
     *
     * Subclass overrides this to alter the behavior of HashMap(Map),
     * clone, and readObject.
     */
//    void createEntry(int hash, K key, V value, int bucketIndex) {
//        Entry<K,V> e = table[bucketIndex];
//        table[bucketIndex] = new Entry<>(hash, key, value, e);
//        size++;
//    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>A return value of {@code null} does not <i>necessarily</i>
     * indicate that the map contains no mapping for the key; it's also
     * possible that the map explicitly maps the key to {@code null}.
     * The {@link #containsKey containsKey} operation may be used to
     * distinguish these two cases.
     *
     * @see #put(Object, Object)
     */
//    public V get(Object key) {
//        if (key == null)
//            return getForNullKey();
//        Entry<K,V> entry = getEntry(key);
//
//        return null == entry ? null : entry.getValue();
//    }

    /**
     * Returns the entry associated with the specified key in the
     * HashMap.  Returns null if the HashMap contains no mapping
     * for the key.
     */
//    final Entry<K,V> getEntry(Object key) {
//        if (size == 0) {
//            return null;
//        }
//
//        int hash = (key == null) ? 0 : hash(key);
//        for (Entry<K,V> e = table[indexFor(hash, table.length)];
//             e != null;
//             e = e.next) {
//            Object k;
//            if (e.hash == hash &&
//                    ((k = e.key) == key || (key != null && key.equals(k))))
//                return e;
//        }
//        return null;
//    }
}
