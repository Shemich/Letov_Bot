package ru.shemich.letovpoem_bot.service;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.shemich.letovpoem_bot.model.UserProfileData;
import ru.shemich.letovpoem_bot.repository.UsersProfileMongoRepository;

import java.util.List;

/**
 * Сохраняет, удаляет, ищет анкеты пользователя.
 *
 */
@Service
public class UsersProfileDataService {

    private UsersProfileMongoRepository profileMongoRepository;

    public UsersProfileDataService(UsersProfileMongoRepository profileMongoRepository) {
        this.profileMongoRepository = profileMongoRepository;
    }

    public List<UserProfileData> getAllProfiles() {
        return profileMongoRepository.findAll();
    }

    public void saveUserProfileData(UserProfileData userProfileData) {
        profileMongoRepository.save(userProfileData);
    }

    public void deleteUsersProfileData(String profileDataId) {
        profileMongoRepository.deleteById(profileDataId);
    }

    public UserProfileData getUserProfileData(long chatId) {
        try {
            return profileMongoRepository.findByChatId(chatId);
        } catch (IncorrectResultSizeDataAccessException e) {
            //e.printStackTrace();
            System.out.println("!!!ERROR!!! " + e);
            return null;
        }
    }


}
