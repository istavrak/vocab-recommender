---
title: Vocab-recommender v1
author: istavrak
layout: post
---
The first working version of the framework is out and supports the extraction and recommendation of static HTML parts (i.e. images, etc.).

This version has already been deployed on my [student server](http://ist-lab.sti2.at/vocab-recommender).
To test it the user can make a GET request to one of the endpoints, e.g.:
```GET http://ist-lab.sti2.at/vocab-recommender/recommendation?url=http://istavrak.com```
or by using curl:
```curl http://ist-lab.sti2.at/vocab-recommender/recommendation?url=http://istavrak.com```

The latest updates are under construction and can always be found at the [github repository](https://github.com/istavrak/vocab-recommender) of the project.
