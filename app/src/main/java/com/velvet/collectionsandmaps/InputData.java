package com.velvet.collectionsandmaps;

public class InputData {

    private boolean progressState;
    private String itemName;
    private String itemExecutionTime;

    public InputData(boolean state, String name, String executionTime){
        progressState = state;
        itemName = name;
        itemExecutionTime = executionTime;

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean getProgressState() {
        return progressState;
    }

    public void setProgressState(boolean progressState) {
        this.progressState = progressState;
    }

    public String getItemExecutionTime() {
        return itemExecutionTime;
    }

    public void setItemExecutionTime(String itemExecutionTime) {
        this.itemExecutionTime = itemExecutionTime;
    }
}
