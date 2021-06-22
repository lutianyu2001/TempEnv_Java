******************
TempEnv Java Class
******************

What is this ?
==============

Generally, many java packages depends on the environment variable,
although some may provide alternative approach using **Sytem Property**
(like `Spark\'s Hadoop Directory Settings <https://cwiki.apache.org/confluence/display/HADOOP2/WindowsProblems>`_),
there's still many packages only accept environment variable.

Unfortunately, Java itself didn\'t provide the function for modifying the system environment variable,
as it violents the idea "cross-platform". Therefore, finding out a way to set environment variable inside the JVM is
very importantly.
Luckily, there are some brilliant fellows found `hacks to do this`_.

.. _`hacks to do this`: https://stackoverflow.com/questions/318239

So why are you creating this repository ?
=========================================

The hacks in stack overflow are only some pieces of code, which lacks integrity and may be difficult to understand for
beginners.
Thus, I choosed one `hack from Tim Ryan`_ that **I think is the best** and
made modifications as well as write Javadoc comments for it, to provide a complete java class for you to invoke easily
and quickly.

.. _`hack from Tim Ryan`: https://stackoverflow.com/a/42964302

Is it cross-platform ?
======================

Accoarding to the discussion on stack overflow, it is suitable for both **Windows** and **Linux**,
but I currently only tested it on Windows 10 with Java 1.8 (`Corretto 1.8.0_292`_).

.. _`Corretto 1.8.0_292`: https://aws.amazon.com/corretto

Are there any documentation ?
=============================

The Javadoc comments are already enough.

License
=======

LGPL v2.1

Thanks
======

`Tim Ryan`_, for his briliant code.

.. _`Tim Ryan`: https://stackoverflow.com/a/42964302

`Mike Rodent`_, his code helped me a lot in understanding Tim\'s code.

.. _`Mike Rodent`: https://stackoverflow.com/a/59522743

Other Things
============

Please feel free to contact me or posting **issues** and **pull requests** !
