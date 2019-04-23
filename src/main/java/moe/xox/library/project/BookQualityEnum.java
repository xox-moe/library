package moe.xox.library.project;

public enum  BookQualityEnum {

    /**
     *   object1.put("qualityId",1);
     *         object1.put("qualityName","无法使用");
     *         list.add(object1);
     *         JSONObject object2 = new JSONObject();
     *         object2.put("qualityId",2);
     *         object2.put("qualityName","破损");
     *         list.add(object2);
     *         JSONObject object3 = new JSONObject();
     *         object3.put("qualityId",3);
     *         object3.put("qualityName","磨损");
     *         list.add(object3);
     *         JSONObject object4 = new JSONObject();
     *         object4.put("qualityId",4);
     *         object4.put("qualityName","有使用痕迹");
     *         list.add(object4);
     *         JSONObject object5 = new JSONObject();
     *         object5.put("qualityId",5);
     *         object5.put("qualityName","全新");
     *         list.add(object5);
     */

    NEW(5,"全新"),
    HENJI(4,"有使用痕迹"),
    MOSUN(3,"磨损"),
    POSUN(2,"破损"),
    DESTROY(1,"无法使用");

    public long id;
    public String name;

    BookQualityEnum(int id,String name){
        this.id = id;
        this.name = name;
    }

    public static String getNameById(long id){
        for (BookQualityEnum value : BookQualityEnum.values()) {
            if (value.id == id)
                return value.name;
        }
        return "不合法数据";
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}
