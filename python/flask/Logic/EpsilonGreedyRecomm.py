import numpy as np
import random

# Initialize parameters. epsilon is exploration rate.
epsilon = 0.4


def Bayes_recommendation(data):
    recommendations = []
    for room in data[1]:
        prior_successes = []
        prior_failures = []
        for i in range(len(data[1][room]["feeds"])):
            success_count = data[1][room]["feeds"][i]["count"]
            total_trials = data[1][room]["memberCount"] * data[1][room]["types"][i]["count"]
            failure_count = total_trials - success_count
            prior_successes.append(success_count + 1)
            prior_failures.append(failure_count + 1)
        # Sample from Beta distribution
        arm_values = [np.random.beta(successes, failures) for successes, failures in
                      zip(prior_successes, prior_failures)]
        # Choose an arm to recommend
        if random.random() > epsilon:
            # Exploit the current best arm
            chosen_arm_idx = np.argmax(arm_values)
        else:
            # Explore a random arm
            chosen_arm_idx = random.randrange(len(arm_values))
        # Calculate question ID for tomorrow
        # If daily is selected
        if chosen_arm_idx == 0:
            # if the last questionID is equal to max id, return the min id
            if data[1][room]["current"][0]["currentDaily"] == data[0]["DAILY"]["max"]:
                selected_questionID = data[0]["DAILY"]["min"]
            else:
                selected_questionID = data[1][room]["current"][0]["currentDaily"] + 1
        elif chosen_arm_idx == 1:
            if data[1][room]["current"][0]["currentGame"] == data[0]["GAME"]["max"]:
                selected_questionID = data[0]["GAME"]["min"]
            else:
                selected_questionID = data[1][room]["current"][0]["currentGame"] + 1
        elif chosen_arm_idx == 2:
            if data[1][room]["current"][0]["currentKnow"] == data[0]["KNOW"]["max"]:
                selected_questionID = data[0]["KNOW"]["min"]
            else:
                selected_questionID = data[1][room]["current"][0]["currentKnow"] + 1
        recomm_for_this_room = dict()
        recomm_for_this_room["roomID"] = room
        recomm_for_this_room["questionID"] = selected_questionID
        recommendations.append(recomm_for_this_room)
    return recommendations