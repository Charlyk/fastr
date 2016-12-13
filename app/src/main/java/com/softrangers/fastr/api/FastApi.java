package com.softrangers.fastr.api;

import com.softrangers.fastr.model.Schedule;
import com.softrangers.fastr.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by eduard on 11.12.16.
 */

public interface FastApi {

    @GET("/api/booking/GetUserInfo")
    Call<ArrayList<User>> getUserInfo(@Query("uname") String userName, @Query("pwd") String password);

    @GET("/api/booking/getdailyftschedule")
    Call<ArrayList<Schedule>> getSchedules(@Query("p_ProgramID") int programId,
                                           @Query("uid") int userId,
                                           @Query("dt") String date);

    @GET("/api/booking/SaveFTStatus")
    Observable<Object> changeScheduleStatus(@Query("p_ProgramID") int programId,
                                            @Query("uid") int userId,
                                            @Query("bdid") String bookDispatchId,
                                            @Query("statusid") String statusId);
}
