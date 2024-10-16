package daos.implementations;

import daos.interfaces.GenericDAO;
import daos.interfaces.ModHistoryDAO;
import models.ModHistory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.UUID;


@RequestScoped
@Named("modDAO")
public class ModHistoryImpl extends GenericDAOImpl<ModHistory, UUID> implements ModHistoryDAO {
    public ModHistoryImpl() {
        super(ModHistory.class);
    }
}
