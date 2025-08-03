import java.util.*;

public class KnapsackHelper {
    public static Item createItemToInclude(Item currentItem, List<Integer> benefits, List<Integer> weights) {
        //Keep track of the items being processed
        List<Integer> nextItems = new ArrayList<>(currentItem.getSelectedItems());
        nextItems.add(currentItem.getId() + 1);

        //Handle an edge case where the list sizes are different
        if (benefits.size() != weights.size())
            throw new IllegalArgumentException("Benefits and weights must have the same size.");

        //Take the next item into consideration while updating the current state
        return new Item(
                currentItem.getId() + 1,
                currentItem.getBenefit() + benefits.get(currentItem.getId() + 1),
                currentItem.getWeight() + weights.get(currentItem.getId() + 1),
                nextItems
        );
    }

    public static Item createItemToExclude(Item currentItem) {
        //Take the next item into consideration while keeping the current state
        return new Item(
                currentItem.getId() + 1,
                currentItem.getBenefit(),
                currentItem.getWeight(),
                new ArrayList<>(currentItem.getSelectedItems())
        );
    }

    public static void updateKnapsackState(Item itemToInclude, State state) {
        //If the added item's benefit is "better", update the state
        if (itemToInclude.getBenefit() >= state.getBenefit()) {
            state.setBenefit(itemToInclude.getBenefit());
            state.setWeight(itemToInclude.getWeight());
            state.setSelectedItems(itemToInclude.getSelectedItems());
        }
    }
}