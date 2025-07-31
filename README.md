# 🚚 SwiftCart Simulator

**A Multi-threaded Java Simulation of an E-Commerce Warehouse Using Concurrency Concepts**

## 📄 Description

**SwiftCart Simulator** is a Java-based concurrent programming project that simulates the core operations of an automated e-commerce warehouse. The system mimics real-world logistics workflows such as:

- 🧾 Order intake  
- 🛒 Picking  
- 📦 Packing  
- 🏷️ Labelling  
- 🔄 Sorting  
- 🚛 Loading and dispatching

Each stage operates in its own thread, enabling efficient, parallel execution of tasks using Java concurrency mechanisms like `Threads`, `BlockingQueue`, `AtomicInteger`, `Semaphores`, and `Synchronized Blocks`.

The simulation processes **600 orders** with random breakdowns, delays, and faults introduced to closely reflect real-world unpredictability. A background supervisor thread monitors the flow and gathers performance metrics in real time.

---

## 🧠 Concurrency Concepts Applied

- **Multithreading** for task-level parallelism  
- **BlockingQueue** for thread-safe communication between stages  
- **AtomicInteger** for race-free counters  
- **Semaphores** to manage shared loading bay access  
- **Synchronized blocks** for safe terminal output  
- **Daemon thread (Supervisor)** for non-blocking monitoring

---

## 📊 Features

- ✅ Simulates full warehouse flow for 600+ client orders  
- ✅ Real-time performance monitoring  
- ✅ Random faults and processing delays for realism  
- ✅ Generates summary report with key stats  
- ✅ Modular, maintainable, and scalable code design

---

## 📁 Project Structure

```plaintext
SwiftCart/
├── ASSIGNMENT.java         # Main simulation driver
├── OrderIntake.java        # Order generator
├── PickingStation.java     # Picker thread
├── PackingStation.java     # Packer thread
├── LabellingStation.java   # Labeller thread
├── Sorter.java             # Sorter thread
├── Loader.java             # Loader thread
├── Truck.java              # Truck dispatcher
├── Box.java, Container.java, Order.java, etc.
├── Supervisor.java         # Monitoring thread
├── Statistics.java         # System metrics collector
└── README.md               # Project description
