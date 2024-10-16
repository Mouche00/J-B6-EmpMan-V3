package services.interfaces;

import models.Leave;

public interface LeaveService extends GenericService<Leave, String> {
    boolean validate(String id);
}
