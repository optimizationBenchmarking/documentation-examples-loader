# [documentation-examples-loader](http://www.github.com/optimizationBenchmarking/documentation-examples-loader/)

[<img alt="Travis CI Build Status" src="https://img.shields.io/travis/optimizationBenchmarking/documentation-examples-loader/master.svg" height="20"/>](https://travis-ci.org/optimizationBenchmarking/documentation-examples-loader/)
[<img alt="Codeship Build Status" src="https://img.shields.io/codeship/7fa56ee0-f0d8-0133-b08b-2640b49afa9e.svg" height="20"/>](https://codeship.com/projects/7fa56ee0-f0d8-0133-b08b-2640b49afa9e/status?branch=master)
[<img alt="CircleCI Build Status" src="https://img.shields.io/circleci/project/optimizationBenchmarking/documentation-examples-loader.svg" height="20"/>](https://circleci.com/gh/optimizationBenchmarking/documentation-examples-loader)
[<img alt="Semaphore Build Status" src="https://semaphoreci.com/api/v1/thomasweise/documentation-examples-loader/branches/master/shields_badge.svg" height="20"/>](https://semaphoreci.com/thomasweise/documentation-examples-loader)
[<img alt="Snap CI Build Status" src="https://img.shields.io/snap-ci/optimizationBenchmarking/documentation-examples-loader/master.svg" height="20"/>](https://snap-ci.com/optimizationBenchmarking/documentation-examples-loader/branch/master)
[<img alt="Wercker Build Status" src="https://img.shields.io/wercker/ci/572469167ed0b05537068c61.svg" height="20"/>](https://app.wercker.com/#applications/572469167ed0b05537068c61)
[<img alt="Shippable Build Status" src="https://img.shields.io/shippable/572468a32a8192902e1e852f.svg" height="20"/>](https://app.shippable.com/projects/572468a32a8192902e1e852f)
[<img alt="AppVeyor Build Status" src="https://img.shields.io/appveyor/ci/thomasWeise/documentation-examples-loader.svg" height="20"/>](https://ci.appveyor.com/project/thomasWeise/documentation-examples-loader)
[<img alt="Drone IO Build Status" src="https://drone.io/github.com/optimizationBenchmarking/documentation-examples-loader/status.png" height="20"/>](https://drone.io/github.com/optimizationBenchmarking/documentation-examples-loader/latest)

This library provides the methods used to download the examples of the [optimizationBenchmarking](http://www.github.com/optimizationBenchmarking/) tool suite. Details about the licensing of this project and the projects it depends on are given in [LICENSE.md](https://github.com/optimizationBenchmarking/documentation-examples-loader/blob/master/LICENSE.md).

## Data Repositories

By default, our example listing and downloading tools use the repository http://optimizationBenchmarking.github.io/documentation-examples/.
However, all job builders have the method `setBaseUri`, allowing you to use any other repository that can be identified by and accessed via a URI or URL. These repositories must have the following structure

    |-- exampleList.txt
    |-- data
    |     |-- firstExample
    |     |       |-- fileList.txt
    |     |       |-- ...
    |     |-- secondExample
    |     |       |-- fileList.txt
    |     |       |-- ...
    |     |--- ...
    
### exampleList.txt

The file `exampleList.txt` provides a list of the examples in the repository. It is a text file where always three lines identify one example. This file has the format

    [minEvaluatorVersion,maxEvaluatorVersion] exampleFolderName
    human readable example title
    HTML description
    
`minEvaluatorVersion` and `maxEvaluatorVersion` identify the minimum and maximum version of the optimization benchmarking evaluator for which the example can be used. They have the format `major.minor.patch`, i.e., follow the [Semantic Versioning](http://semver.org/) guides. The reason why we specify versions is that the available evaluator modules may change over time and even the structure of the configuration or data files itself might change. Both `minEvaluatorVersion` and `maxEvaluatorVersion` are optional, i.e., you could write something like `[0.8.5,]` to indicate only the minimal required version (here, 0.8.5) or `[,1.0.2]` for making the example available only up to evaluator version 1.0.2. Finally, you can even write `[,]` to indicate that the example is available for all versions.

`exampleFolderName` is the folder name of the example, relative to `data`. In the repository structure displayed above, this could be `firstExample` or `secondExample`. This name (as well as any other file or folder name used) should not cause any problems within an URL or URI.

`human readable example title` is the title of the example. It can be any plain text and might be copied into a HTML document into `<em>` or `<h[1-6]>` tags.

 `HTML description` is a single line of description in HTML format. It may contain multiple `<p>` tags. If it does not start with `<p>`, the example loader will wrap it into `<p>...</p>` automatically.
 
 There can be an arbitrary number of such line-triples in the `exampleList.txt` file, each identifying another example.
 
 ### fileList.txt
 
Each example folder contains a file `fileList.txt` which identifies the resources belong to an example. Each line of this file has the following shape:

    [minEvaluatorVersion,maxEvaluatorVersion] resourcePathRelativeToExampleFolder
    
`minEvaluatorVersion` and `maxEvaluatorVersion` have the same meaning as in `exampleList.txt` : A resource will only loaded by the example loader if the evaluator version matches the resource version range.

`resourcePathRelativeToExampleFolder` is the path of the resource relative to the example folder, e.g., `data/firstExample` in the structure displayed above. Each such path must identify a file. Files may additionally be structured in folders, however the example downloader *will only preserve the file name itself*. In other words, if you download `data/firstExample/1.0.6/config.xml` into folder `/home/user/x/`, the downloader will produce `/home/user/x/config.xml`. Special case: If the resource name ends with `zip`, e.g., `data/firstExample/1.0.6/results.zip`, the archive will be directly extracted into the destination folder instead, into `/home/user/x/` in our example.
 