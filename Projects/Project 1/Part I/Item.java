import java.util.*;

public class Item {
    private int id;
    private int benefit;
    private int weight;
    private List<Integer> selectedItems;

    public Item(int id, int benefit, int weight, List<Integer> selectedItems) {
        this.id = id;
        this.benefit = benefit;
        this.weight = weight;
        this.selectedItems = new ArrayList<>(selectedItems);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "ID: " + id + ", Benefit: " + benefit + ", Weight: " + weight + ", Selected items: " + selectedItems;
    }
}