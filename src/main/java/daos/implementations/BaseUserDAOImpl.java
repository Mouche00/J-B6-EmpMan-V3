package daos.implementations;

import daos.interfaces.BaseUserDAO;
import models.BaseUser;

import java.util.UUID;

public class BaseUserDAOImpl extends GenericDAOImpl<BaseUser, UUID> implements BaseUserDAO {
    public BaseUserDAOImpl() {
        super(BaseUser.class);
    }
}
