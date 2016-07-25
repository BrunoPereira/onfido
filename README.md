# Onfido Test

# What is it?
This is a implementation of a Vending Machine.
It tries to mimic the behaviour of a physical vending machine (with all its benefits and limitations)
Currently we can only use British coins with this machine, but as soon as other currencies are added, 
we can create vending machines that accept the new currencies.
The British coins are: "1p", "2p", "5p", "10p", "20p", "50p", "£1", "£2".

# Restrictions
The Change feature has some limitations.
Ex: 
- The product costs 4p.
- The machine has 1 5p coin and 10 2p coins.
- If the user pays with 10p, we can't give the change.
- But if the machine only had 10 2p coins, then we can give the correct change.


# How to use
This is a maven project, so the jar is easily build.
Just run "mvn clean install" in the project directory.

The application is a "fat" jar that has all the dependencies inside it.

Examples:
VendingMachine vendingMachine = new VendingMachine (new BritishCoins());

# Assumptions
- The machine when it's constructed doesnt get an initial load. Is empty.
- This means that loading the machine before the first buy is something that the operator has to do, it's not imposed by any restictions. The machine works empty.
- The operator can load the machine at any given time, that won't reset the costumer current process.
- All money introduced by the costumers is stored in a safe.
- The machine only satisfies one customer at a time. If there's more than one, it's treated as if it was the same.

# Author
Bruno Pereira - https://github.com/BrunoPereira



