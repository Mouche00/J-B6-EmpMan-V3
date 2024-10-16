package daos.implementations;

import daos.interfaces.GenericDAO;
import daos.interfaces.SalaryHistoryDAO;
import jdk.jfr.Name;
import models.ModHistory;
import models.SalaryHistory;

import javax.enterprise.context.RequestScoped;
import java.util.UUID;

@RequestScoped
@Name("salaryDAO")
public class SalaryHistoryImpl extends GenericDAOImpl<SalaryHistory, UUID> implements SalaryHistoryDAO {
    public SalaryHistoryImpl() {
        super(SalaryHistory.class);
    }
}
