package org.example;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.groups.GroupsArray;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VKRepository {
    private final int AppID;
    private final String Code;
    private final VkApiClient vk;
    private final UserActor actor;

    public VKRepository() throws ClientException, ApiException {
        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        AppID = getAppID();
        Code = getCode();
        actor = new UserActor(AppID, Code);
    }

    public List<UserFull> getUsersFieldsByName(String name, Fields... fields) throws ClientException, ApiException {
        return vk.users().search(actor).q(name).fields(fields).execute().getItems();
    }

    public UserFull getUserByUniversityName(List<UserFull> users, String name) {
        for (UserFull u : users) {
            if (u.getUniversities() != null && !u.getUniversities().isEmpty() && u.getUniversities().getFirst().getName().equals(name)) {
                return u;
            }
        }
        return null;
    }

    public boolean isSubscribed(UserFull user, HashSet<Integer> groupIDs) throws ClientException, ApiException {
        if (!user.getIsClosed()) {
            List<Integer> groups = vk.users().getSubscriptions(actor).userId(user.getId()).execute().getGroups().getItems();
            for (Integer id : groups) {
                if (groupIDs.contains(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public UserFull getUserByUniversityNameAndGroupIDs(List<UserFull> users, String university, HashSet<Integer> groupIDs) throws ClientException, ApiException {
        UserFull res = getUserByUniversityName(users,university);
        if (res != null){
            return res;
        }
        for (UserFull u : users){
            if (isSubscribed(u,groupIDs)){
                return u;
            }

            try{
                TimeUnit.MILLISECONDS.sleep(250);
            } catch ( InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    private int getAppID() {
        try {
            return Integer.parseInt(Files.readString(Paths.get("C:\\Users\\user\\javaprojectvkconfig\\Id.txt")));
        } catch (IOException e) {
            return -1;
        }
    }

    private String getCode() {
        try {
            return Files.readString(Paths.get("C:\\Users\\user\\javaprojectvkconfig\\Code.txt"));
        } catch (IOException e) {
            return null;
        }
    }
}
