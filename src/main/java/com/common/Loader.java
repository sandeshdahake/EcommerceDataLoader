package com.common;

public interface Loader {
    public static final String API_KEY = "FTer735ETlnMOrm38gc3hm3Et4DtkaJjKsN";
   // public static final String API_KEY ="PIekn3DpcNtiZG1gn9uKAulTW2qREGIs4VL";

    //Change the delay value based on the API supported hits
    public static final Long WAIT = 4000L;
    public void load(Object object);
}
