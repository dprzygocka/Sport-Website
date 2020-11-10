package server.sport.utils;

import org.springframework.data.domain.Sort;

public class SortCheck {
    public static Sort.Direction getSortDirection(String direction){
        if(direction.equals("asc")){
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }
}
