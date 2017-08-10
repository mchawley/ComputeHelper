package com.example.manishchawley.commutehelper.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.manishchawley.commutehelper.util.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by manishchawley on 22/10/16.
 */

public class Commuter implements Parcelable{

    private String commuterId;                 //commuter id
    private String commuterName;            //commuter name
    private int commuterAge;                //commuter age
    private String imageURL;                //commuter image url
    private String commuterDescription;     //commuter description
    private String commuterGender;          //commuter gender
    private int commuterDistance;           //commuter distance from user

    public Commuter(){}

    protected Commuter(Parcel in) {
        commuterId = in.readString();
        commuterName = in.readString();
        commuterAge = in.readInt();
        imageURL = in.readString();
        commuterDescription = in.readString();
        commuterGender = in.readString();
        commuterDistance = in.readInt();
    }

    public static final Creator<Commuter> CREATOR = new Creator<Commuter>() {
        @Override
        public Commuter createFromParcel(Parcel in) {
            return new Commuter(in);
        }

        @Override
        public Commuter[] newArray(int size) {
            return new Commuter[size];
        }
    };

    public String getCommuterId() {
        return commuterId;
    }

    public void setCommuterId(String commuterId) {
        this.commuterId = commuterId;
    }

    public String getCommuterName() {
        return commuterName;
    }

    public void setCommuterName(String commuterName) {
        this.commuterName = commuterName;
    }

    public int getCommuterAge() {
        return commuterAge;
    }

    public void setCommuterAge(int commuterAge) {
        this.commuterAge = commuterAge;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCommuterDescription() {
        return commuterDescription;
    }

    public void setCommuterDescription(String commuterDescription) {
        this.commuterDescription = commuterDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(commuterId);
        dest.writeString(commuterName);
        dest.writeInt(commuterAge);
        dest.writeString(imageURL);
        dest.writeString(commuterDescription);
        dest.writeString(commuterGender);
        dest.writeInt(commuterDistance);
    }

    public String getCommuterGender() {
        return commuterGender;
    }

    public void setCommuterGender(String commuterGender) {
        this.commuterGender = commuterGender;
    }

    public int getCommuterDistance() {
        return commuterDistance;
    }

    public void setCommuterDistance(int commuterDistance) {
        this.commuterDistance = commuterDistance;
    }


    public static Commuter createCommuterFromUser(){
        Commuter commuter = new Commuter();
        commuter.setCommuterId(User.getUser().getUserID());
        commuter.setCommuterName(User.getUser().getName());
        commuter.setImageURL(User.getUser().getImageURL().toString());
        return commuter;
    }

    public void setCommuter(ValueEventListener listener) throws NullCommuterIdException {
        if(commuterId==null) throw new NullCommuterIdException();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Constants.COMMUTER_DATABASE_KEY);
        database.child(commuterId).setValue(this);
        database.child(commuterId).addValueEventListener(listener);

    }

    public class NullCommuterIdException extends Throwable {
        @Override
        public String getMessage() {
            return "Commuter ID cannot be Null";
        }
    }
}

//    public static final Creator<Commuter> CREATOR = new Creator<Commuter>() {
//        @Override
//        public Commuter createFromParcel(Parcel in) {
//            return new Commuter(in);
//        }
//
//        @Override
//        public Commuter[] newArray(int size) {
//            return new Commuter[size];
//        }
//    };
//
//    public void setName(String name){
//        this.name = name;
//    }
//
//    public String getName(){
//        return  name;
//    }
//
//    public void setAge(int age){
//        this.age = age;
//    }
//
//    public int getAge(){
//        return age;
//    }
//
//    public void setGender(int gender){
//        this.gender = gender;
//    }
//
//    public int getGender(){
//        return gender;
//    }
//
//    public void setCurrenttrip(Trip currenttrip){
//        this.currenttrip = currenttrip;
//    }
//
//    public Trip getCurrenttrip(){
//        return currenttrip;
//    }
//
//    public void setPrefrences(List<String> prefrences){
//        this.prefrences = prefrences;
//    }
//
//    public List<String> getPrefrences(){
//        return prefrences;
//    }
//
//   private static final String TAG = Commuter.class.getName();
//
//    private String name;
//    private int age;
//    private int gender;
//    private Trip currenttrip;
//    private List<String> prefrences;
//    private String imageurl;
//    private float distancefromuser;
//    private String facebookuserid;
//    private String facebookusertoken;
//
//    private String userid;
//
//    public Commuter(){}
//
//    public Commuter(String facebookuserid, String facebookusertoken){
//        this.facebookuserid = facebookuserid;
//        this.facebookusertoken = facebookusertoken;
//    }
//
//    public Commuter(AccessToken accessToken, Profile facebookProfile){
//        //this.accessToken = accessToken;
//        //this.facebookProfile = facebookProfile;
//    }
//
//    public Commuter(String name, int age, int gender, Trip currenttrip, List<String> prefrences){
//        this.name = name;
//        this.age = age;
//        this.gender = gender;
//        this.currenttrip = currenttrip;
//        this.prefrences = prefrences;
//    }
//
//    public Commuter(String name, int age, int gender, Trip currenttrip, List<String> prefrences, float distancefromuser){
//        this.name = name;
//        this.age = age;
//        this.gender = gender;
//        this.currenttrip = currenttrip;
//        this.prefrences = prefrences;
//        this.distancefromuser = distancefromuser;
//    }
//
//    protected Commuter(Parcel in) {
//        name = in.readString();
//        age = in.readInt();
//        gender = in.readInt();
//        currenttrip = in.readParcelable(Trip.class.getClassLoader());
//        prefrences = in.createStringArrayList();
//        imageurl = in.readString();
//        distancefromuser = in.readFloat();
//        facebookuserid = in.readString();
//        facebookusertoken = in.readString();
//        userid = in.readString();
//    }
//
//    public void setImageurl(String imageurl){
//        this.imageurl = imageurl;
//    }
//
//    public String getImageurl(){
//        return imageurl;
//    }
//
//    public void setDistancefromuser(float distancefromuser){
//        this.distancefromuser = distancefromuser;
//    }
//
//    public float getDistancefromuser(){
//        return distancefromuser;
//    }
//
//    public String getStringDistancefromuser(){
//        return String.valueOf(distancefromuser);
//    }
//
//    public void setFacebookuserid(String facebookuserid){
//        this.facebookuserid = facebookuserid;
//    }
//
//    public String getFacebookuserid(){
//        return facebookuserid;
//    }
//
//    public void setFacebookUserTokenString(String facebookusertoken){
//        this.facebookusertoken = facebookusertoken;
//    }
//
//    public String getFacebookUserTokenString(){
//        return facebookusertoken;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeInt(age);
//        dest.writeInt(gender);
//        dest.writeParcelable(currenttrip, flags);
//        dest.writeStringList(prefrences);
//        dest.writeString(imageurl);
//        dest.writeFloat(distancefromuser);
//        dest.writeString(facebookuserid);
//        dest.writeString(facebookusertoken);
//        dest.writeString(userid);
//    }
//}
