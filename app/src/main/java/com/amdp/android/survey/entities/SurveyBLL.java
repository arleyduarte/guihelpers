package com.amdp.android.survey.entities;



import com.amdp.android.entity.APIEntity;
import com.amdp.android.entity.EntityBLL;

import java.util.ArrayList;
import java.util.List;


public class SurveyBLL extends EntityBLL {

    private static SurveyBLL ourInstance = new SurveyBLL();

    private SurveyRegister currentSurvey;

    private Survey selectedSurvey;
    private ArrayList<Survey> surveys = new ArrayList<Survey>();


    private SurveyBLL(){

    }

    public static SurveyBLL getInstance() {
        return ourInstance;
    }

    public SurveyRegister getCurrentSurvey() {

        return currentSurvey;
    }

    public void setCurrentSurvey(SurveyRegister currentSurvey) {
        this.currentSurvey = currentSurvey;
    }

    public void setSelectedSurvey(Survey selectedSurvey) {
        this.selectedSurvey = selectedSurvey;
    }

    public Survey getSelectedSurvey() {
        return selectedSurvey;
    }

    public ArrayList<Survey> getSurveys() {
        return surveys;
    }

    public void setSurveys(ArrayList<Survey> surveys) {
        this.surveys = surveys;
    }


    public void addSurvey(Survey survey){
        if (survey != null && survey.getName() != null) {

            if (containsName(survey, surveys)) {
                Survey toUpdate = (Survey) findByName(survey.getName());
                surveys.remove(toUpdate);
            }
            surveys.add(survey);

        }
    }

    protected boolean containsName(Survey apiEntity, ArrayList<Survey> vItems) {
        boolean contains = true;
        if (findByName(apiEntity.getName()) == null) {
            contains = false;
        }
        return contains;
    }

    public Survey findByName(String name) {
        Survey apiEntity = null;
        for (Survey aux : surveys) {
            if (aux.getName() != null && aux.getName().equals(name)) {
                apiEntity = aux;
            }
        }
        return apiEntity;
    }

    public void restore() {
        List<Survey> localSurveys = Survey.listAll(Survey.class);
        for (Survey survey : localSurveys) {
            addSurvey(survey);
        }
    }

    public void clear() {
        surveys.clear();
        Survey.deleteAll(Survey.class);
    }

    private static boolean isSaving = false;

    public void save() {

        if (!isSaving) {


            new Thread(new Runnable() {
                public void run() {
                    isSaving = true;
                    ArrayList<APIEntity> rAlerts = (ArrayList<APIEntity>) surveys.clone();

                    for (APIEntity aux : rAlerts) {
                        Survey survey = (Survey) aux;

                        try {
                            survey.save();
                        } catch (Exception ex) {

                        }

                        isSaving = true;
                    }
                    isSaving = false;

                }
            }).start();
        }

    }

}
