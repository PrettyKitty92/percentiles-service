package psm.percentile.web.service;

import psm.percentile.common.model.user.ApplicationUser;

public interface IBabySampleGenerator {


    ApplicationUser generateBabyMeasurementForUser(String username);
}
