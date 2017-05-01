package entity;

public class PU {
   private static String PU_NAME = "pu_development";
   
   public static void setPU_Name(String s){
     PU_NAME =s;
   }
   public static String getPersistenceUnitName(){
     //System.out.println("PU_NAME ::::::::--------> "+PU_NAME);
     return PU_NAME;
   }
}