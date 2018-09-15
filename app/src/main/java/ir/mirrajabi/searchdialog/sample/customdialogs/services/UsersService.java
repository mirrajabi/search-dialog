package ir.mirrajabi.searchdialog.sample.customdialogs.services;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.sample.customdialogs.models.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface UsersService {
	@POST("api/users")
	Call<ArrayList<UserModel>> getFakeUsersBasedOnASearchTag(@Body String tag);
}
