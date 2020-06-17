package com.delicate.mule.auth;

import com.delicate.mule.model.Record;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class AdminAuthService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据传进来的account_id与mapping去权限表中查询是否记录
     *
     * @param account_id
     * @param mapping
     * @return
     */
    public boolean hasPermission(Long account_id, String mapping) {
        String hanPermission = "select ar.account_id from (" +
                "select rp.role_id from role_permission rp " +
                "inner join permission p on rp.permission_id = p.id " +
                "where p.mapping = ?1 ) as t " +
                "inner join account_role ar on t.role_id = ar.role_id " +
                "where ar.account_id = ?2 ";
        if (StringUtils.isEmpty(mapping)) {
            return false;
        }
        Query query = entityManager.createNativeQuery(hanPermission);
        query.setParameter(1, mapping).setParameter(2, account_id);
        List resultList = query.getResultList();
        if (CollectionUtils.isEmpty(resultList)) {
            return false;
        }
        return true;
    }


    public List<Record> getAdminList() {
        String getAdminListSQL = "select a.name, ar.account_id,ar.role_id, r.name as role_name from account a, account_role ar, role r " +
                "where a.id = ar.account_id and ar.role_id = r.id " +
                "order by role_id asc";
        List<Record> adminLists = entityManager.createNativeQuery(getAdminListSQL, "adminLists").getResultList();
        return adminLists;
    }


    public boolean FindAccountById(String name) {
        String sql = " select * from account where name = ?1 ";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, name);
        Object singleResult = query.getSingleResult();
        return singleResult != null;
    }

}
