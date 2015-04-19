package com.delivery.apis.params;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s on 19-04-2015.
 */
public class Parameters {
    @XStreamAlias("SignInModule")
    public SignIn signIn = null;
    //public List<Adapter> Adapter  = new ArrayList<Adapter>();

    /*@XStreamAlias("MobileServerParams")
    @XStreamConverter(GenericParamsConverter.class)
    public MobileServerParams mobileServerParams = null;

    @XStreamAlias("MobileUser")
    @XStreamConverter(GenericParamsConverter.class)
    public UserCredentialParams userParams = null;*/
}
