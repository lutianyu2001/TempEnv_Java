import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Field;


/**
 * ClassName:   TempEnv                                                                           <br>
 * Description: A [static class] for setting temporary environment variable for current JVM       <br>
 * @author      Lu Tianyu
 * @version     v2.0 (2021.06)
 *
 * Remarks:                                                                                       <br>
 *   Original Author: Tim Ryan @ Stack Overflow                                                   <br>
 *   Source:          https://stackoverflow.com/a/42964302                                        <br>
 *
 */
public class TempEnv {

    private TempEnv() {} // use private to hide constructor as it is unneeded

    /**
     * [Only for Test]  Print out a map
     * @param map  the map you want to print
     */
    private static void printMap(Map<?, ?> map) {
        map.forEach((key, value) -> System.out.println("\nKey:\n\t" + key + "\nValue:\n\t" + value));
    }

    /**
     * Get current JVM's environment variable map (by reference)
     * @return  the map we get, modifying it will modify current JVM's environment variables
     * @throws  RuntimeException
     */
    private static Map<String, String> getJVMEnvMap() {
        try{
            Map<String, String> sysEnv = System.getenv();
            Class<?> cl = sysEnv.getClass();
            Field field = cl.getDeclaredField("m");
            field.setAccessible(true);
            return (Map<String, String>) field.get(sysEnv); // this is the JVMEnv
        }catch(Exception e) {
            throw new RuntimeException("Error: Unable to access writable environment variable map !" +
                    "Specific Exception is: " + e.getStackTrace());
        }
    }

    /**
     * Set environment variables by pass in a map
     * @param varMap  the map of the environment variables you are going to set
     *
     * Example:                                                                                   <br>
     *      Map<String, String> envs = new HashMap<String, String>();                             <br>
     *      envs.put("JAVA_VERSION","1.8.0_292");                                                 <br>
     *      envs.put("JAVA_EDITION","Corretto");                                                  <br>
     *      TempEnv.setEnv(envs);                                                                 <br>
     */
    public static void setEnv(Map<String, String> varMap) {
        getJVMEnvMap().putAll(varMap);
    }

    /**
     * [Overload]  Set a single environment variable by pass in a string array
     * @param varPair  the environment variable you are going to set, varPair[0] - key, varPair[1] - value
     * @throws         IllegalArgumentException
     *
     * Example:                                                                                   <br>
     *      String[] env = {"JAVA_VERSION","1.8.0_292"}                                           <br>
     *      TempEnv.setEnv(env);                                                                  <br>
     */
    public static void setEnv(String[] varPair) {
        if(varPair.length != 2) throw new IllegalArgumentException("Error: input array's length "
                + varPair.length + " does not meet requirement, only accept array that length = 2 !");

        Map<String, String> tempMap = new HashMap<String, String>();
        tempMap.put(varPair[0], varPair[1]);
        getJVMEnvMap().putAll(tempMap);
    }

    /**
     * Remove environment variables by pass in a string array of environment variables' keys
     * @param keys  the keys of environment variables you are going to remove
     * @return      a map showing the removed environment variable pairs,
     *                may be empty map if none of the keys are found
     */
    public static Map<String, String> removeEnv(String[] keys) {
        Map<String, String> JVMEnv = getJVMEnvMap();
        Map<String, String> removedEnvs = new HashMap<String, String>();

        for(String k : keys) {
            String val = JVMEnv.remove(k); // will return the value, if value == null then the key does not exist
            if(val != null) removedEnvs.put(k, val);
        }
        return removedEnvs;
    }

    /**
     * [Overload]  Remove a single environment variable by pass in a string of environment variable's key
     * @param key  the key of environment variable you are going to remove
     * @return     a map showing the removed environment variable pair, may be empty map if the key is not found
     */
    public static Map<String, String> removeEnv(String key) {
        Map<String, String> removedEnv = new HashMap<String, String>();

        String val = getJVMEnvMap().remove(key);
        if(val != null)  removedEnv.put(key, val);
        return removedEnv;
    }

    /**
     * Test the TempEnv class
     */
    public static void testTempEnv() {

        printMap(getJVMEnvMap());

        Map<String, String> envs = new HashMap<String, String>();
        envs.put("JAVA_VERSION","1.8.0_292");
        envs.put("JAVA_EDITION","Corretto");
        setEnv(envs);

        System.out.println("-------------------------------------------------------------------------");
        getJVMEnvMap().forEach((key, value) -> {
            if(key=="JAVA_EDITION" && value=="Corretto") System.out.println("Test 1 ok !");
            else if(key=="JAVA_VERSION" && value=="1.8.0_292") System.out.println("Test 2 ok !");
        });

        // ----------------------------------------------------------------------------------------------

        String[] removeVal = {"JAVA_VERSION"};
        Map<String, String> removedEnv = removeEnv(removeVal);

        getJVMEnvMap().forEach((key, value) -> {
            if(key=="JAVA_EDITION" && value=="Corretto") System.out.println("Test 3 ok !");
        });

        removedEnv.forEach((key, value) -> {
            if(key=="JAVA_VERSION" && value=="1.8.0_292") System.out.println("Test 4 ok !");
        });

        // ----------------------------------------------------------------------------------------------

        removedEnv = removeEnv("JAVA_EDITION");

        getJVMEnvMap().forEach((key, value) -> {
            if(key=="JAVA_EDITION" && value=="Corretto") System.out.println("Test 5 Failed !");
        });

        removedEnv.forEach((key, value) -> {
            if(key=="JAVA_EDITION" && value=="Corretto") System.out.println("Test 5 ok !");
        });

        // ----------------------------------------------------------------------------------------------

        String[] env = {"JAVA_COMPANY","Amazon"};
        setEnv(env);

        getJVMEnvMap().forEach((key, value) -> {
            if(key=="JAVA_COMPANY" && value=="Amazon") System.out.println("Test 6 ok !");
        });

        removedEnv = removeEnv("JAVA_COMPANY");

        removedEnv.forEach((key, value) -> {
            if(key=="JAVA_COMPANY" && value=="Amazon") System.out.println("Test 7 (the last one) ok !");
        });

        System.out.println("-------------------------------------------------------------------------");

    }

}
