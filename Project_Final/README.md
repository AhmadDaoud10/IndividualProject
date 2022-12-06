The project consists of five classes:

Botstartup:
This class initializes the bot; it creates a JDA instance using the discord token, and iniziates a Listener.

Commands:
This class creates a new user which will store the information from the user and uses listener adapter to read events.

onGuildMessageReceived: This class reads Strings sent within the chat and provides a response based on them (the input). It acts based on the current step, which is an attribute from the class useer, within the process (e.g. acquiring name, height, weight, etc.) using a switch statement. After the information acquisition is complete, the bot sends a sport suggestion based on this information from an arrayList that contains three sports adequate for the user's data. Once the user accepts a suggestion, the attribute sport of the user is set to that sport, and taining recomendations can begin based on their time availability.

User:
This class is a template to create an object that stores the information of the user chatting with the bot, with appropriate getter and setter methods.

Sport:
This class is a template to create an object that is each indivudal sport (e.g. basketball, baseball).

Training:
This class is a template to create an object representing training suggestions
