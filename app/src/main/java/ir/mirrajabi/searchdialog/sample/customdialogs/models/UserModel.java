package ir.mirrajabi.searchdialog.sample.customdialogs.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.core.Searchable;


public class UserModel implements Searchable {
	@SerializedName("id")
	private int mId;
	@SerializedName("name")
	private String mName;
	@SerializedName("lastName")
	private String mLastName;
	@SerializedName("age")
	private int mAge;
	@SerializedName("phoneNumbers")
	private ArrayList<String> mPhoneNumbers;
	
	public UserModel(int age, int id, String lastName, String name, ArrayList<String> phoneNumbers) {
		mAge = age;
		mId = id;
		mLastName = lastName;
		mName = name;
		mPhoneNumbers = phoneNumbers;
	}
	
	@Override
	public String getTitle() {
		return mName + " " + mLastName;
	}
	
	public int getAge() {
		return mAge;
	}
	
	public UserModel setAge(int age) {
		mAge = age;
		return this;
	}
	
	public int getId() {
		return mId;
	}
	
	public UserModel setId(int id) {
		mId = id;
		return this;
	}
	
	public String getLastName() {
		return mLastName;
	}
	
	public UserModel setLastName(String lastName) {
		mLastName = lastName;
		return this;
	}
	
	public String getName() {
		return mName;
	}
	
	public UserModel setName(String name) {
		mName = name;
		return this;
	}
	
	public ArrayList<String> getPhoneNumbers() {
		return mPhoneNumbers;
	}
	
	public UserModel setPhoneNumbers(ArrayList<String> phoneNumbers) {
		mPhoneNumbers = phoneNumbers;
		return this;
	}
}
