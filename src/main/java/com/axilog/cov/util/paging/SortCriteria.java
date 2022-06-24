package com.axilog.cov.util.paging;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.EnumUtils;

import com.axilog.cov.dto.base.AbstractQuery;
import com.axilog.cov.util.paging.SortCriteria.Direction;

public class SortCriteria implements Iterable<Map.Entry<String, Direction>> {

    private static final String KEYS_SEPARATOR = ",";
    private static final String DIRECTION_ASSIGNER = ":";

    public enum Direction {
        ASC, DESC;
    }

    private Map<String, Direction> keys = new HashMap<>();

    public SortCriteria(AbstractQuery query) {
        this(query.getSort(), query.getSupportedSortKeys());
    }

    public SortCriteria(String sortStr, String[] supportedKeys) {
        if (isEmpty(sortStr)) {
            return;
        }

        String[] strSplit = sortStr.split(KEYS_SEPARATOR);

        for (String keyDirectionStr : strSplit) {
            String[] keyDirectionTuple = keyDirectionStr.split(DIRECTION_ASSIGNER);
            if (keyDirectionTuple.length != 2) {
                continue;
            }

            String key = keyDirectionTuple[0].trim();
            if (key.isEmpty()) {
                continue;
            }

            String dirStr = keyDirectionTuple[1].toUpperCase().trim();

            if (!EnumUtils.isValidEnum(Direction.class, dirStr)) {
                continue;
            }

            Direction dir = Direction.valueOf(dirStr);

            if (ArrayUtils.contains(supportedKeys, key)) {
                this.keys.put(key, dir);
            }
        }
    }

    public Direction get(String key) {
        return keys.get(key);
    }

    @Override
    public Iterator<Map.Entry<String, Direction>> iterator() {
        return keys.entrySet().iterator();
    }
}
