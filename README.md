# Row-and-Column-ACL-Security
Short summary: Filtering and securing Hibernate entities </br>
For more information see the full pdf Row and column security with ACL filtering in Java Spring.pdf

## General overview
<p>
Row security with ACL filtering is already implemented in Java spring with the @PostFilter and
@PreFilter annotation and the SpEL expression hasPermissoion. However, a need may arise to filter out
the columns too as an additional security measure using the available ACL information. For example, we
might have an entity, containing sensitive information about a person, like social security number, and
telephone number that can only be accessed by certain users, or certain roles. Moreover, we may need
to specify the permission the users or roles need to have, such as BasePermission.READ,
BasePermission.ADMINISTRATION or any type of permission (including custom permissions) and the
security identities (user or role) in order to be able to have access to the column. In order to do this,
custom advice has to be created, because we need access to the permissions of each row, firstly to
determine that access to the row is granted, and then to compare the defined requirements for column
security with the same granted permissions to determine if access to a certain column is granted. This
means that row filtering and column filtering both have to be implemented together. The implication of
this is that the PostFilter or the PreFilter cannot be used i.e custom implementation of the same
features is needed. I have developed a few solutions for filtering the return result (equivalent to
PostFilter hasPermission plus column filtering). However the same can be done for filtering the
arguments of a method (equivalent to PreFilter hasPermission plus column filtering) very easily just by
changing the code relating to acquiring the collection source.
</p>
<p>
The first problem that has to be considered in order to determine the way we approach the
implementation is the way Hibernate handles projection. When using Hibernate’s criteria and
Hibernate’ projections to select one or multiple columns, the return result can only be cast as a list of
arrays of objects. Each array of objects (each element of the list) is either an attribute or an aggregation
function result. Therefore, there are two options available regarding this issue:
</p>
1. The projection is done using Hibernate’s criteria and Hibernate’ projections in the DAO layer, which
means that the service layer is aware of the projection, and it returns a list of arrays of objects. This
means that before the method is called, the criteria (required attribute) has to be initialized so that it
filters the columns by the SIDs (principal or role) the user grants access to in a custom annotation to
each column. The permissions for each row are obtained after the method returns, so additional
filtering has to be done after the method returns in order to compare the permissions for each
column.
2. The DAO method returns a list of the entities and is not aware of the column filtering, the advice
handles the filtering removing the proxy from the returned entities and in case access if granted for
the object (row), by setting the forbidden fields values (if any) to null if they are Object subclasses 
and if they are primitive values, to some minimum value or false in case of boolean meaning that
setter methods have to be present.

<p>
The second conceptual problem is if we should assume that each instance of the same class
(entity) has the same permissions assigned to the same SIDs. So far we have assumed the opposite. If
we reexamine the previous two approaches based on the new assumption, then it wouldn’t be
necessary to obtain the permissions for each object. Instead we can obtain the permissions for one row,
which will result in a much better performance. However, it will also reduce the flexibility and the
applicability of the solution.
</p>
<p>
All four approaches will be analyzed in detail in the following sections along with their advantages
and disadvantages. 
</p>