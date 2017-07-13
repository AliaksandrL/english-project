package translator.util;

/**
 * Created by Администратор on 09.07.2017.
 */
public class StaticValues {
    private int authenticatedUserId;
    private static StaticValues statValues;

    public static void setAuthenticatedUserId(int authUserId) {
        if(authUserId!=0 && statValues==null)
        {
            statValues = new StaticValues();
            statValues.authenticatedUserId=authUserId;
        }
    }

    public static int getAuthenticatedUserId() {
        return statValues.authenticatedUserId;
    }


}
