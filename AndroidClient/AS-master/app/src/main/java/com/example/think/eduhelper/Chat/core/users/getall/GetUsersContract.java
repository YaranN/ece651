package com.example.think.eduhelper.Chat.core.users.getall;
import com.example.think.eduhelper.Chat.models.User;

import java.util.List;


public interface GetUsersContract {
    interface View {
        void onGetAllUsersSuccess(List<User> users);

        void onGetAllUsersFailure(String message);

        void onGetSelectedUsersSuccess(List<User> users);

        void onGetSelectedUsersFailure(String message);
    }

    interface Presenter {
        void getAllUsers();
        void getHelpers();
        void getSelectedUsers();
    }

    interface Interactor {
        void getAllUsersFromFirebase();

        void getHelpersFromFirebase();
        void getSelectedUsersFromFirebase();
    }

    interface OnGetAllUsersListener {
        void onGetAllUsersSuccess(List<User> users);

        void onGetAllUsersFailure(String message);
    }
}
