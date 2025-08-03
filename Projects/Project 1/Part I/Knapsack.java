import java.util.*;

public class Knapsack {
    public State breadthFirstSearch(List<Integer> benefits, List<Integer> weights, int maximumWeight) {
        //Initialize a queue to hold considered items and a state to track benefit and weight while adding a starting point
        Queue<Item> queue = new LinkedList<>();
        State state = new State(0, 0, new ArrayList<>());

        queue.add(new Item(-1, 0, 0, new ArrayList<>()));

        //Process items while the queue isn't empty
        while (!queue.isEmpty()) {
            //Item being processed gets removed from the front of the queue
            Item currentItem = queue.poll();

            //Handle empty collections
            if (benefits.isEmpty() || weights.isEmpty()) {
                return state;
            }

            //Handle IndexOutOfBoundsException
            if (currentItem.getId() >= benefits.size() - 1)
                continue;

            //Creation of an item object which is to be included in the knapsack
            Item itemToInclude = KnapsackHelper.createItemToInclude(currentItem, benefits, weights);
            //Creation of an item object which is to be excluded from the knapsack
            Item itemToExclude = KnapsackHelper.createItemToExclude(currentItem);

            //If the item to be included doesn't exceed the maximum weight, add the item to the queue
            if (itemToInclude.getWeight() <= maximumWeight) {
                queue.add(itemToInclude);
                KnapsackHelper.updateKnapsackState(itemToInclude, state);
            }
            queue.add(itemToExclude); //Item to be excluded is added for further exploration
        }
        //Output the result providing the state obtained by the Breadth First Search algorithm
        return state;
    }

    public State depthFirstSearch(List<Integer> benefits, List<Integer> weights, int maximumWeight) {
        //Initialize a stack to hold considered items, state to track benefit and weight while adding a starting point
        Stack<Item> stack = new Stack<>();
        State state = new State(0, 0, new ArrayList<>());

        stack.push(new Item(-1, 0, 0, new ArrayList<>()));

        //Process items while the queue isn't empty
        while (!stack.isEmpty()) {
            //Item being processed gets removed from the top of the stack
            Item currentItem = stack.pop();

            //Handle empty collections
            if (benefits.isEmpty() || weights.isEmpty()) {
                return state;
            }

            //Handle IndexOutOfBoundsException
            if (currentItem.getId() >= benefits.size() - 1)
                continue;

            //Creation of an item object which is to be included in the knapsack
            Item itemToInclude = KnapsackHelper.createItemToInclude(currentItem, benefits, weights);
            //Creation of an item object which is to be excluded from the knapsack
            Item itemToExclude = KnapsackHelper.createItemToExclude(currentItem);

            //If the item to be included doesn't exceed the maximum weight, add the item to the stack
            if (itemToInclude.getWeight() <= maximumWeight) {
                stack.push(itemToInclude);
                KnapsackHelper.updateKnapsackState(itemToInclude, state);
            }
            stack.push(itemToExclude); //Item to be excluded is added for further exploration
        }
        //Output the result providing the state obtained by the Depth First Search algorithm
        return state;
    }
}