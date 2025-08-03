import java.util.*;

public class State {
    private int benefit;
    private int weight;
    private List<Integer> selectedItems;

    public State(int benefit, int weight, List<Integer> selectedItems) {
        this.benefit = benefit;
        this.weight = weight;
        this.selectedItems = new ArrayList<>(selectedItems);
    }

    public int getBenefit() {
        return benefit;
    }

    public void setBenefit(int benefit) {
        this.benefit = benefit;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Integer> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Integer> selectedItems) {
        this.selectedItems = selectedItems;
    }

    @Override
    public String toString() {
        return "\nBenefit: " + benefit + ", Weight: " + weight + ", Selected items: " + selectedItems;
    }
}