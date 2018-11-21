package com.example.think.eduhelper.Chat.core.users.getall;

import com.example.think.eduhelper.Chat.models.User;
import com.example.think.eduhelper.Post.core.getPost.GetPostConstractor;

import java.util.List;


public class GetUsersPresenter implements GetUsersContract.Presenter, GetUsersContract.OnGetAllUsersListener {
    private GetUsersContract.View mView;
    private GetUsersInteractor mGetUsersInteractor;

    public GetUsersPresenter(GetUsersContract.View view) {
        this.mView = view;
        mGetUsersInteractor = new GetUsersInteractor(this);
    }

    @Override
    public void getAllUsers() {
        mGetUsersInteractor.getAllUsersFromFirebase();
    }

    @Override
    public void getHelpers() {
        mGetUsersInteractor.getHelpersFromFirebase();
    }

    @Override
    public void getSelectedUsers() {
        mGetUsersInteractor.getSelectedUsersFromFirebase();
    }

    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        mView.onGetAllUsersSuccess(users);
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mView.onGetAllUsersFailure(message);
    }

}
