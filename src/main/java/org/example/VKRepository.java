package org.example;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
