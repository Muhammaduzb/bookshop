package uz.bookclub.bookclubapplication.service;


import uz.bookclub.bookclubapplication.model.Result;
import uz.bookclub.bookclubapplication.payload.RegisterReq;

public interface UserService {
    Result saveUser(RegisterReq registerReq);
}