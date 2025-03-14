import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.stream.IntStream;

public class Controller {
    private View view;
    private TestRunner testRunner;
    private Cache cache;
    private MainMemory mainMemory;

    public Controller(View view) {
        this.view = view;
        this.view.addStartButtonListener(new StartButtonListener());
        this.testRunner = new TestRunner();
        this.view.addRunTestsButtonListener(new RunTestsButtonListener());
        this.view.addAddDataButtonListener(new AddDataButtonListener());
    }

    class RunTestsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String results = testRunner.runAllTests();
            view.setOutputTextArea(results);
        }
    }

//    class StartButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//                int cacheSize = view.getCacheSize();
//                int mainMemorySize = view.getMainMemorySize();
//                String mappingStrategy = view.getMappingStrategy();
//                int associativity = view.getAssociativity();
//                String replacementPolicy = view.getReplacementPolicy();
//                int numAccesses = view.getNumAccesses();
//
//                mainMemory = new MainMemory(mainMemorySize);
//
//                MappingStrategy strategy = switch (mappingStrategy) {
//                    case "Direct Mapping" -> new DirectMapping();
//                    case "Set Associative Mapping" -> new SetAssociative(associativity);
//                    case "Associative Mapping" -> new FullyAssociative();
//                    default -> throw new IllegalArgumentException("Invalid Mapping Strategy");
//                };
//
//                ReplacementPolicy policy = switch (replacementPolicy) {
//                    case "FIFO" -> new FIFOPolicy();
//                    case "LRU" -> new LRUPolicy();
//                    case "Random" -> new RandomPolicy();
//                    default -> throw new IllegalArgumentException("Invalid Replacement Policy");
//                };
//
//                cache = new Cache(cacheSize, associativity, replacementPolicy, 1, mainMemory);
//
//                // Simulating memory accesses
//                int[] accesses = IntStream.range(0, numAccesses)
//                        .map(i -> (int) (Math.random() * mainMemorySize))
//                        .toArray();
//
//                for (int address : accesses) {
//                    cache.read(address);
//                }
//
//                StringBuilder results = new StringBuilder();
//                results.append("Simulation Results:\n");
//                results.append("Hit Rate: ").append(cache.getHitRate()).append("%\n");
//                results.append("Miss Rate: ").append(cache.getMissRate()).append("%\n");
//
//                view.setOutputTextArea(results.toString());
//            } catch (Exception ex) {
//                view.setOutputTextArea("Error: " + ex.getMessage());
//            }
//        }
//    }

    class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int cacheSize = view.getCacheSize();
                int mainMemorySize = view.getMainMemorySize();
                String mappingStrategy = view.getMappingStrategy();
                int associativity = view.getAssociativity();
                String replacementPolicy = view.getReplacementPolicy();
                int numAccesses = view.getNumAccesses();

                // Input validation
                if (cacheSize <= 0 || mainMemorySize <= 0 || numAccesses <= 0) {
                    view.setOutputTextArea("Error: Cache size, memory size, and number of accesses must be greater than zero.");
                    return;
                }

                if ("Set Associative Mapping".equals(mappingStrategy) && associativity <= 0) {
                    view.setOutputTextArea("Error: Associativity must be greater than zero for Set Associative Mapping.");
                    return;
                }

                // Initialize main memory
                mainMemory = new MainMemory(mainMemorySize);

                // Initialize cache
                int blockSize = 1; // For simplicity, blockSize is set to 1 in this example
                int mapping = switch (mappingStrategy) {
                    case "Direct Mapping" -> 1;
                    case "Fully Associative" -> cacheSize;
                    case "Set Associative Mapping" -> associativity;
                    default -> throw new IllegalArgumentException("Invalid Mapping Strategy");
                };

                cache = new Cache(cacheSize, mapping, replacementPolicy, blockSize, mainMemory);

                // Simulate random accesses
                Random rand = new Random();
                for (int i = 0; i < numAccesses; i++) {
                    int address = rand.nextInt(mainMemorySize);
                    cache.read(address);
                }

                // Update table and results
                view.updateCacheTable(cache.getCacheState());
                view.setOutputTextArea("Simulation started successfully.\n" + cache.getStatistics());

            } catch (NumberFormatException ex) {
                view.setOutputTextArea("Error: Please enter valid numerical inputs.");
            } catch (Exception ex) {
                view.setOutputTextArea("Error: " + ex.getMessage());
            }
        }
    }


    ///////////////////
//    class AddDataButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//                int address = view.getMemoryAddress();
//                int value = view.getValueToWrite();
//
//                MainMemory mainMemory = new MainMemory(view.getMainMemorySize());
//                Cache cache = new Cache(view.getCacheSize(),
//                        view.getAssociativity(),
//                        view.getReplacementPolicy(),
//                        1, mainMemory);
//
//                // Write the value to both cache and memory
//                cache.write(address, value);
//
//                // Update the output area
//                String message = String.format("Added value %d at address %d.\n%s",
//                        value, address, cache.getStatistics());
//                view.setOutputTextArea(message);
//            } catch (Exception ex) {
//                view.setOutputTextArea("Error: " + ex.getMessage());
//            }
//        }
//    }

//    class AddDataButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//                // Ensure that the simulation has been started
//                if (cache == null || mainMemory == null) {
//                    view.setOutputTextArea("Please start the simulation first.");
//                    return;
//                }
//
//                int address = view.getMemoryAddress();
//                int value = view.getValueToWrite();
//
//                // Write the value to main memory
//                mainMemory.write(address, value);
//
//                // Write the value to cache
//                cache.write(address, value);
//
//                // Update the output area with updated statistics
//                String message = String.format("Added value %d at address %d.\n%s",
//                        value, address, cache.getStatistics());
//                view.setOutputTextArea(message);
//            } catch (NumberFormatException ex) {
//                view.setOutputTextArea("Error: Please enter valid numerical values for address and value.");
//            } catch (Exception ex) {
//                view.setOutputTextArea("Error: " + ex.getMessage());
//            }
//        }
//    }

//    class AddDataButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//                if (cache == null || mainMemory == null) {
//                    view.setOutputTextArea("Please start the simulation first.");
//                    return;
//                }
//
//                int address = view.getMemoryAddress();
//                int value = view.getValueToWrite();
//
//                // Write data to cache
//                cache.write(address, value);
//
//                // Update the GUI table
//                String[][] cacheState = cache.getCacheState();
//                view.updateCacheTable(cacheState);
//
//                // Update output area
//                String message = String.format("Added value %d at address %d.\n%s",
//                        value, address, cache.getStatistics());
//                view.setOutputTextArea(message);
//            } catch (Exception ex) {
//                view.setOutputTextArea("Error: " + ex.getMessage());
//            }
//        }
//    }

    class AddDataButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (cache == null || mainMemory == null) {
                    view.setOutputTextArea("Error: Please start the simulation first.");
                    return;
                }

                int address = view.getMemoryAddress();
                int value = view.getValueToWrite();

                // Write data to cache
                cache.write(address, value);

                // Update the GUI table
                view.updateCacheTable(cache.getCacheState());

                // Display updated cache statistics
                view.setOutputTextArea(String.format(
                        "Data added successfully.\n%s", cache.getStatistics()
                ));

            } catch (NumberFormatException ex) {
                view.setOutputTextArea("Error: Invalid input for address or value.");
            } catch (Exception ex) {
                view.setOutputTextArea("Error: " + ex.getMessage());
            }
        }
    }


    ///////////////////
}
