h1. JTS Onboarding

h2. Purpose

Help new project members with project coding environment.

[http://en.wikipedia.org/wiki/Onboarding]

h2. TOC
{toc}

h1. Eclipse Workspace Steps

h2. Eclipse workspace configuration steps

{info:title=Remote files}
[http://jump-the-shark.googlecode.com/svn/trunk/core/checkstyle/src/main/resources/jts-codestyle/]
{info}

h3. Output Filtering

* Window->Preferences->Java->Compiler->Building
* Make sure "Filtered Resources" includes ".svn/"

h3. Code style/formatting

* Window->Preferences->Java->Code Style->Formatter->Import...
[jts-format.xml|http://jump-the-shark.googlecode.com/svn/trunk/core/checkstyle/src/main/resources/jts-codestyle/jts-format.xml]

h3. Import organization

* Window->Preferences->Java->Code Style->Organize Imports->Import...
[jts-importorder|http://jump-the-shark.googlecode.com/svn/trunk/core/checkstyle/src/main/resources/jts-codestyle/jts-importorder]

h3. Member sort order

* Window->Preferences->Java->Appearance->Members Sort Order
* There is no import here, so make your settings match:
[jts-sort-order.png|http://jump-the-shark.googlecode.com/svn/trunk/core/checkstyle/src/main/resources/jts-codestyle/jts-sort-order.png]

{info}
# *Members should be sorted by category.*
## Types
## Static Fields
## Static Initializers
## Static Methods
## Fields
## Initializers
## Constructors
## Methods
# *Members in the same category should be sorted by visibility.*
## Public
## Protected
## Default
## Private
# *Within a category/visibility combination, members should be sorted alphabetically.*
#* currently not enforced by checkstyle
{info}

h3. Compiler settings

* Window->Preferences->Java->Compiler
* Set the compiler compliance level to 1.6.

h3. Compiler errors & warnings

* Window->Preferences->Java->Compiler->Errors/Warnings

{info}
*The following warnings are suggested.*

* *Code Style*
** Method with a constructor name
** Potential programming problems:
** Assignment has no effect
** Accidental boolean assignment
** 'finally' does not complete normally
** Using a char array in string concatentation
** Hidden catch block
** Inexact type match for vararg arguments
* *Name shadowing and conflicts*
** all except "Local variable" hiding
* *Deprecated and restricted API*
** all
* *Unnecessary code*
** all except "Unnecessary 'else' statement"
* *Generic types*
** all except "Generic type parameter declared with final type bound"
* *Annotations*
** Annotation is used as super interface
** Enable @SuppressWarnings annotations
{info}

h3. Team Ignored Resources
* Add patterns:
** .project
** .settings
** .classpath
** .checkstyle
** target

h3. XML Formatting

* Window->Preferences->XML->XML Files->Editor
** Line width = 140
** Indent using spaces
** Indentation size = 3

h3. Ant File Formatting
* Ant->Editor->Formatter
** Tab size = 3
** Uncheck "Use tab character instead of spaces"
** Maximum line with = 140

h3. Checkstyle

*Checkstyle is used to enforce good programming style.*

# Install Checkstyle
#* The Eclipse Checkstyle plugin can be found at: [http://eclipse-cs.sourceforge.net/]
# Import JTS Checks:
#* Window->Preferences->Checkstyle
#* Click "New..."
#* Set the Type to "External Configuration File"
#* Set the Name to "JTS Checks" (important)
#* Download "[jts-checkstyle.xml|http://jump-the-shark.googlecode.com/svn/trunk/core/checkstyle/src/main/resources/jts-codestyle/jts-checkstyle.xml]" and put in workspace folder
#* Set Location to location to downloaded file
#* Suggested: Check "Protect Checkstyle configuration file".
#* Click "Ok".
#* Select "JTS Checks"
#* Select "Set as Default"
# Import JTS Checks for Tests:
#* Click "New..."
#* Set the Type to "External Configuration File"
#* Set the Name to "JTS Checks for Tests" (important)
#* Download "[jts-checkstyle-tests.xml|http://jump-the-shark.googlecode.com/svn/trunk/core/checkstyle/src/main/resources/jts-codestyle/jts-checkstyle-tests.xml]" and put in workspace folder
#* Set Location to location to downloaded file
#* Suggested: Check "Protect Checkstyle configuration file".
#* Click "Ok".
#* Click "Ok".

h4. Configure checkstyle on projects

# Set checkstyle configuration
#* Select all projects
#* Project->Close Project
#* Copy "[jts-checkstyle.xml|http://jump-the-shark.googlecode.com/svn/trunk/core/checkstyle/src/main/resources/jts-codestyle/jts-checkstyle.xml]" to each project as ".checkstyle" (look for .project)
# Enable Checks on all projects
#* For each project:
#** Project->Properties->Checkstyle
#** Check "Checkstyle active for this project"
#** Click "Ok"

h3. Eclipse Save Actions

Eclipse can perform certain actions  whenever you save a file.&nbsp; These  actions can be customized through the  Eclipse UI.&nbsp;

To get started,  follow [these steps|http://eclipseone.wordpress.com/2009/12/13/automatically-format-and-cleanup-code-every-time-you-save].

{info}

From the link...

Eclipse has a wonderful feature that formats according to your coding standards. It handles things like indentation, where curly braces are placed, if blank lines should occur between field declaration and hundreds of other options.

However, to invoke this formatting, you have to tell Eclipse to do this every time you’ve edited the code. You _can_ do this using Ctrl+Shift+F but (1) you have to do that every time and    (2) you have to remember to do it in the first place (and we all know how good developers are at remembering things).

Eclipse sports a feature called Save Actions that makes formatting code easy. With this feature enabled, every time you save the file, Eclipse formats the code to your formatting preferences _and_ does some cleanup of the code (eg. remove unused imports). All done automatically and for free.

How to enable automatic formatting and cleanup

By default automatic formatting and cleanup is disabled. To enable it, do the following:
# Go to *Window > Preferences > Java > Editor > Save Actions*.
# Select *Perform the selected actions on save*.
# Select *Format Source code*. Make sure Format all lines is selected (comment from tdoty: this is actually up to the user).
# Make sure *Organize imports* is selected.
# Select *Additional actions*.
# Click Ok, edit some code, save it and watch Eclipse format it automatically.
{info}