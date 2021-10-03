# ViewUranus-Sunhacks2021

## Inspiration
Nobody on our team has ever gone outside, looked up at the sky and said, "Oh hey, there's Uranus!" We decided that that needed to end... as soon as we figured out how to find it. 

## What it does
This application tells users the best time to view celestial objects based on location, weather, moon phase, and planet rise/set times. If the weather is clear and the moon isn't too bright, it will tell you which days have optimal visibility.

As for viewing Uranus, the application displays date, right ascension, declination, observable times, and constellation data to help aspiring astronomers and Uranus lovers find it in the sky.

## How we built it
View Uranus was built in Java, drawing JSON data from various publicly available APIs.

## Challenges we ran into
We met up against quite a few challenges. For one, none of us really know that much about astronomy, so we had to familiarize ourselves with a number of new concepts, including things like planetary ascension, calculating moon phases, and the various factors that affect visibility. 

We also struggled to find data on the visibility of planetary bodies and ways of calculating the location of an astronomical object relative to a viewer on earth. Finding useful APIs and parsing complicated data was also a considerable challenge.

## Accomplishments that we're proud of
We're proud of how it all came together -- it looks nice, works nice, and it's not buggy (that we know of... yet). In particular, we're proud of the algorithms we developed for calculating moon phase, as well as the StarGaze class that combines data on local weather and moon brightness to assess visibility. We're also proud of finding workable APIs and figuring out how to clean up the data so it would be usable for our purposes. There's a lot out there, but it takes time to find and it's rarely straightforward.

## What we learned
- Astronomy is hard
- New astronomical concepts like calculating moon phase and planetary orbits 
- How difficult it is to clean up data (sift through all the noise)
- The factors that affect the visibility of celestial objects
- How to find Uranus
- The tighter the deadline, the more trouble IdeaJ likes to give you

## What's next for View Uranus
We hope to update the application so that it can tell you exactly when and where to look in the sky to find not only Uranus, but other astronomical objects as well. It has to be user friendly, because our ultimate goal is to drive interest in astronomy. In terms of functionality, we want to improve our assessment of visibility and eventually deploy the application as a web app on Google Cloud.
