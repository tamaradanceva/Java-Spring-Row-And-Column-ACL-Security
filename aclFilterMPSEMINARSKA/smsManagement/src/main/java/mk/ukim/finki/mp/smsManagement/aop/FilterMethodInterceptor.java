package mk.ukim.finki.mp.smsManagement.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class FilterMethodInterceptor implements MethodInterceptor {

	@Autowired
	private MutableAclService mutableAclService;
	
	private MutableAclService getMutableAclService() {
		return mutableAclService;
	}

	private void setMutableAclService(MutableAclService mutableAclService) {
		this.mutableAclService = mutableAclService;
	}

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		//get username and authorities
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String username = auth.getName();
	      Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	      ArrayList<String> roles= new ArrayList<String>();
	      Iterator<SimpleGrantedAuthority> iterator = authorities.iterator();
	      while(iterator.hasNext()){
	    	  SimpleGrantedAuthority sga=iterator.next();
	    	  String role=sga.getAuthority();
	    	  roles.add(role);
	      }
	      System.out.println("USERNAME"+username);
	      for(int i=0;i<roles.size();i++){
	    	  System.out.println("ROLES"+roles.get(0));
	      }
		System.out.println("BEFORE METHOD EXECUTION");
	
		System.out.println("BLAAAAAAAAAAAAAAAAAAAAAAA");
		
		Method [] methods=FilterMethodInterceptor.class.getClassLoader().loadClass("mk.ukim.finki.mp.smsManagement.service.SmsService").getMethods();
		
		for(int i=0;i<methods.length;i++){
			System.out.println("METHOD NAME: "+methods[i].getName());
			Annotation [] alist= methods[i].getAnnotations();
			for(int j=0;j<alist.length;j++){
				System.out.println("	ANNOTATION: "+alist[j].getClass().toString());
			}
			if(methods[i].getClass().getAnnotation(MyAclFilter.class)!=null){
				System.out.println("ANNOTATION FOUND"+methods[i].getName());
			}
		}
		
		System.out.println("METHOD INVOCATOIN TO STRING: "+methodInvocation.toString());
		Method m= methodInvocation.getMethod();
		System.out.println("METHOD TO STRING: "+m.toString()+" METHOD TO GENERIC STRING: "+m.toGenericString());
		Annotation [] annotations= m.getAnnotations();
		Annotation aclFilter=null;
		BasePermission basePerm=null;
		CumulativePermission cumPerm=null;
		System.out.println("ANNOTATINOS LENGTH:"+annotations.length+ m.getName());
		if(m.isAnnotationPresent(MyAclFilter.class))
			System.out.println("YAY, IT FOUND IT !");
		for(int i=0;i<annotations.length;i++){
			if(annotations[i] instanceof MyAclFilter){
				System.out.println("FILTER IS FOUND");
				MyAclFilter filter=(MyAclFilter)annotations[i];
				if(filter.typeOfPermission().equals("BasePermission")){
					System.out.println("PRED SWITCH");
					switch(filter.permission()){
					case "READ":
						basePerm= (BasePermission) BasePermission.READ;
						System.out.println("READ E");
						break;
					case "ADMINISTRATION":
						basePerm= (BasePermission) BasePermission.ADMINISTRATION;
						break;
					case "CREATE":
						basePerm=(BasePermission) BasePermission.CREATE;
						break;
					case "DELETE":
						basePerm=(BasePermission) BasePermission.DELETE;
						break;
					case "WRITE":
						basePerm=(BasePermission) BasePermission.WRITE;
						break;
					}
				}
				else if(filter.typeOfPermission().equals("CumulativePermission")){
					//spravi se so cumulative
				}
				else {
					//ne filtriraj
				}
			}
		}
		//System.out.println("FROM ANNOTATION:"+basePerm.toString());
		
		Object result=methodInvocation.proceed();
		
		ArrayList<Object> listobj=(ArrayList<Object>)result;
		ArrayList<Integer> ids= new ArrayList<Integer>();
		for(int i=0;i<listobj.size();i++){
			Class c= listobj.get(i).getClass();
			Method getId=c.getMethod("getId", null);
			if(getId !=null) {
				System.out.println("SUCCESS GETID IS DETECTED"+getId.getReturnType().toString());
			try{
				int id=(int)getId.invoke(listobj.get(i), null);
				System.out.println(id);
				ids.add(new Integer(id));
			}
			catch(Exception e){
				System.out.println(e.toString());
			}
			
			}
			else {
				ids.add(new Integer(0));
			
			}
		}
		
		for(int i=0;i<ids.size();i++){
			System.out.println("OID "+i+" "+ids.get(0).toString());
		}
			
			// create oids 
		ArrayList<ObjectIdentity> oids= new ArrayList<ObjectIdentity>();
		if(listobj.size()>=1){
			for(int i=0;i<ids.size();i++){
				ObjectIdentity oid= new ObjectIdentityImpl(listobj.get(0).getClass(),ids.get(i)); 
				oids.add(oid);
			}
		}
		for(int i=0;i<oids.size();i++){
			ObjectIdentityImpl oid= (ObjectIdentityImpl)oids.get(i);
			System.out.println("OIDS LIST "+i+" :"+oid.toString());
		}
		//read acls by oid map
		Map<ObjectIdentity, Acl> map=mutableAclService.readAclsById(oids);
		ArrayList<Integer> deleteItems= new ArrayList<Integer>();
	
//		//list of permission defined for the object
//		
//		
//		//for each object of the collection
		for(int i=0;i<oids.size();i++){
		AclImpl acl=(AclImpl)map.get(oids.get(i));
		System.out.println("ACL_IMPL :"+acl.toString());
		List<AccessControlEntry> entries=acl.getEntries();
		
		boolean canBeKept=false;
		for(int j=0;j<entries.size();j++){
			AccessControlEntryImpl ace= (AccessControlEntryImpl)entries.get(j);
			System.out.println(ace);
			Sid sid= ace.getSid();
			System.out.println("SID CLASS:"+sid.getClass().toString());
			String sidString="";
			if(sid.getClass().toString().equals("class org.springframework.security.acls.domain.PrincipalSid")){
				PrincipalSid ps= (PrincipalSid)sid;
				sidString=ps.getPrincipal();
				System.out.println(ps.getPrincipal());
			}
			else if(sid.getClass().toString().equals("class org.springframework.security.acls.domain.GrantedAuthoritySid")){
				GrantedAuthoritySid gas= (GrantedAuthoritySid)sid;
				sidString=gas.getGrantedAuthority();
				System.out.println(gas.getGrantedAuthority());
			}
			else{
			}
			boolean sidMatch=false;
				if(sidString!=""){
					if(username.equals(sidString))
					sidMatch=true;
					else {
						for(int n=0;n<roles.size();n++){
							if(roles.get(n).equals(sidString)) sidMatch=true;
						}
					}
				}
		if(sidMatch==true){
			//check permissions
			//if permissions match
			//canbeKept=true;
			System.out.println("SID MATCH! "+sidString);
		}
		else {
			System.out.println("SID NOT MATCH! "+sidString);
		}
		
		}
		if(canBeKept==false){
			System.out.println("add item to be deleted later "+i);
			deleteItems.add(new Integer(i));
		}
	
		}
		
		//delete from objects
		for(int i=0;i<deleteItems.size();i++){
			System.out.println("index to be delted"+deleteItems.get(i));
			boolean b=listobj.remove(listobj.get(deleteItems.get(i)));
			System.out.println("successful deletion: "+b);
		}
		
		
	//	System.out.println(listobj.get(0).getClass().toString());
		
		
		System.out.println("AFTER METHOD EXECUTION");
		return (Object)listobj;
	}

}
