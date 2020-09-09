package com.sprjjs.book.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TOrderExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andRidIsNull() {
            addCriterion("rid is null");
            return (Criteria) this;
        }

        public Criteria andRidIsNotNull() {
            addCriterion("rid is not null");
            return (Criteria) this;
        }

        public Criteria andRidEqualTo(Integer value) {
            addCriterion("rid =", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidNotEqualTo(Integer value) {
            addCriterion("rid <>", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidGreaterThan(Integer value) {
            addCriterion("rid >", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidGreaterThanOrEqualTo(Integer value) {
            addCriterion("rid >=", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidLessThan(Integer value) {
            addCriterion("rid <", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidLessThanOrEqualTo(Integer value) {
            addCriterion("rid <=", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidIn(List<Integer> values) {
            addCriterion("rid in", values, "rid");
            return (Criteria) this;
        }

        public Criteria andRidNotIn(List<Integer> values) {
            addCriterion("rid not in", values, "rid");
            return (Criteria) this;
        }

        public Criteria andRidBetween(Integer value1, Integer value2) {
            addCriterion("rid between", value1, value2, "rid");
            return (Criteria) this;
        }

        public Criteria andRidNotBetween(Integer value1, Integer value2) {
            addCriterion("rid not between", value1, value2, "rid");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(String value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(String value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(String value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(String value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(String value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(String value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLike(String value) {
            addCriterion("uid like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotLike(String value) {
            addCriterion("uid not like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<String> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<String> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(String value1, String value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(String value1, String value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andOidIsNull() {
            addCriterion("oid is null");
            return (Criteria) this;
        }

        public Criteria andOidIsNotNull() {
            addCriterion("oid is not null");
            return (Criteria) this;
        }

        public Criteria andOidEqualTo(String value) {
            addCriterion("oid =", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotEqualTo(String value) {
            addCriterion("oid <>", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidGreaterThan(String value) {
            addCriterion("oid >", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidGreaterThanOrEqualTo(String value) {
            addCriterion("oid >=", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidLessThan(String value) {
            addCriterion("oid <", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidLessThanOrEqualTo(String value) {
            addCriterion("oid <=", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidLike(String value) {
            addCriterion("oid like", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotLike(String value) {
            addCriterion("oid not like", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidIn(List<String> values) {
            addCriterion("oid in", values, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotIn(List<String> values) {
            addCriterion("oid not in", values, "oid");
            return (Criteria) this;
        }

        public Criteria andOidBetween(String value1, String value2) {
            addCriterion("oid between", value1, value2, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotBetween(String value1, String value2) {
            addCriterion("oid not between", value1, value2, "oid");
            return (Criteria) this;
        }

        public Criteria andStaIsNull() {
            addCriterion("sta is null");
            return (Criteria) this;
        }

        public Criteria andStaIsNotNull() {
            addCriterion("sta is not null");
            return (Criteria) this;
        }

        public Criteria andStaEqualTo(String value) {
            addCriterion("sta =", value, "sta");
            return (Criteria) this;
        }

        public Criteria andStaNotEqualTo(String value) {
            addCriterion("sta <>", value, "sta");
            return (Criteria) this;
        }

        public Criteria andStaGreaterThan(String value) {
            addCriterion("sta >", value, "sta");
            return (Criteria) this;
        }

        public Criteria andStaGreaterThanOrEqualTo(String value) {
            addCriterion("sta >=", value, "sta");
            return (Criteria) this;
        }

        public Criteria andStaLessThan(String value) {
            addCriterion("sta <", value, "sta");
            return (Criteria) this;
        }

        public Criteria andStaLessThanOrEqualTo(String value) {
            addCriterion("sta <=", value, "sta");
            return (Criteria) this;
        }

        public Criteria andStaLike(String value) {
            addCriterion("sta like", value, "sta");
            return (Criteria) this;
        }

        public Criteria andStaNotLike(String value) {
            addCriterion("sta not like", value, "sta");
            return (Criteria) this;
        }

        public Criteria andStaIn(List<String> values) {
            addCriterion("sta in", values, "sta");
            return (Criteria) this;
        }

        public Criteria andStaNotIn(List<String> values) {
            addCriterion("sta not in", values, "sta");
            return (Criteria) this;
        }

        public Criteria andStaBetween(String value1, String value2) {
            addCriterion("sta between", value1, value2, "sta");
            return (Criteria) this;
        }

        public Criteria andStaNotBetween(String value1, String value2) {
            addCriterion("sta not between", value1, value2, "sta");
            return (Criteria) this;
        }

        public Criteria andAidIsNull() {
            addCriterion("aid is null");
            return (Criteria) this;
        }

        public Criteria andAidIsNotNull() {
            addCriterion("aid is not null");
            return (Criteria) this;
        }

        public Criteria andAidEqualTo(Integer value) {
            addCriterion("aid =", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotEqualTo(Integer value) {
            addCriterion("aid <>", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThan(Integer value) {
            addCriterion("aid >", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThanOrEqualTo(Integer value) {
            addCriterion("aid >=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThan(Integer value) {
            addCriterion("aid <", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThanOrEqualTo(Integer value) {
            addCriterion("aid <=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidIn(List<Integer> values) {
            addCriterion("aid in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotIn(List<Integer> values) {
            addCriterion("aid not in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidBetween(Integer value1, Integer value2) {
            addCriterion("aid between", value1, value2, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotBetween(Integer value1, Integer value2) {
            addCriterion("aid not between", value1, value2, "aid");
            return (Criteria) this;
        }

        public Criteria andPaymentIsNull() {
            addCriterion("payment is null");
            return (Criteria) this;
        }

        public Criteria andPaymentIsNotNull() {
            addCriterion("payment is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentEqualTo(Double value) {
            addCriterion("payment =", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentNotEqualTo(Double value) {
            addCriterion("payment <>", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentGreaterThan(Double value) {
            addCriterion("payment >", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentGreaterThanOrEqualTo(Double value) {
            addCriterion("payment >=", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentLessThan(Double value) {
            addCriterion("payment <", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentLessThanOrEqualTo(Double value) {
            addCriterion("payment <=", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentIn(List<Double> values) {
            addCriterion("payment in", values, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentNotIn(List<Double> values) {
            addCriterion("payment not in", values, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentBetween(Double value1, Double value2) {
            addCriterion("payment between", value1, value2, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentNotBetween(Double value1, Double value2) {
            addCriterion("payment not between", value1, value2, "payment");
            return (Criteria) this;
        }

        public Criteria andPlacedIsNull() {
            addCriterion("placed is null");
            return (Criteria) this;
        }

        public Criteria andPlacedIsNotNull() {
            addCriterion("placed is not null");
            return (Criteria) this;
        }

        public Criteria andPlacedEqualTo(Date value) {
            addCriterion("placed =", value, "placed");
            return (Criteria) this;
        }

        public Criteria andPlacedNotEqualTo(Date value) {
            addCriterion("placed <>", value, "placed");
            return (Criteria) this;
        }

        public Criteria andPlacedGreaterThan(Date value) {
            addCriterion("placed >", value, "placed");
            return (Criteria) this;
        }

        public Criteria andPlacedGreaterThanOrEqualTo(Date value) {
            addCriterion("placed >=", value, "placed");
            return (Criteria) this;
        }

        public Criteria andPlacedLessThan(Date value) {
            addCriterion("placed <", value, "placed");
            return (Criteria) this;
        }

        public Criteria andPlacedLessThanOrEqualTo(Date value) {
            addCriterion("placed <=", value, "placed");
            return (Criteria) this;
        }

        public Criteria andPlacedIn(List<Date> values) {
            addCriterion("placed in", values, "placed");
            return (Criteria) this;
        }

        public Criteria andPlacedNotIn(List<Date> values) {
            addCriterion("placed not in", values, "placed");
            return (Criteria) this;
        }

        public Criteria andPlacedBetween(Date value1, Date value2) {
            addCriterion("placed between", value1, value2, "placed");
            return (Criteria) this;
        }

        public Criteria andPlacedNotBetween(Date value1, Date value2) {
            addCriterion("placed not between", value1, value2, "placed");
            return (Criteria) this;
        }

        public Criteria andReceiptIsNull() {
            addCriterion("receipt is null");
            return (Criteria) this;
        }

        public Criteria andReceiptIsNotNull() {
            addCriterion("receipt is not null");
            return (Criteria) this;
        }

        public Criteria andReceiptEqualTo(Date value) {
            addCriterion("receipt =", value, "receipt");
            return (Criteria) this;
        }

        public Criteria andReceiptNotEqualTo(Date value) {
            addCriterion("receipt <>", value, "receipt");
            return (Criteria) this;
        }

        public Criteria andReceiptGreaterThan(Date value) {
            addCriterion("receipt >", value, "receipt");
            return (Criteria) this;
        }

        public Criteria andReceiptGreaterThanOrEqualTo(Date value) {
            addCriterion("receipt >=", value, "receipt");
            return (Criteria) this;
        }

        public Criteria andReceiptLessThan(Date value) {
            addCriterion("receipt <", value, "receipt");
            return (Criteria) this;
        }

        public Criteria andReceiptLessThanOrEqualTo(Date value) {
            addCriterion("receipt <=", value, "receipt");
            return (Criteria) this;
        }

        public Criteria andReceiptIn(List<Date> values) {
            addCriterion("receipt in", values, "receipt");
            return (Criteria) this;
        }

        public Criteria andReceiptNotIn(List<Date> values) {
            addCriterion("receipt not in", values, "receipt");
            return (Criteria) this;
        }

        public Criteria andReceiptBetween(Date value1, Date value2) {
            addCriterion("receipt between", value1, value2, "receipt");
            return (Criteria) this;
        }

        public Criteria andReceiptNotBetween(Date value1, Date value2) {
            addCriterion("receipt not between", value1, value2, "receipt");
            return (Criteria) this;
        }

        public Criteria andDeliverIsNull() {
            addCriterion("deliver is null");
            return (Criteria) this;
        }

        public Criteria andDeliverIsNotNull() {
            addCriterion("deliver is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverEqualTo(Date value) {
            addCriterion("deliver =", value, "deliver");
            return (Criteria) this;
        }

        public Criteria andDeliverNotEqualTo(Date value) {
            addCriterion("deliver <>", value, "deliver");
            return (Criteria) this;
        }

        public Criteria andDeliverGreaterThan(Date value) {
            addCriterion("deliver >", value, "deliver");
            return (Criteria) this;
        }

        public Criteria andDeliverGreaterThanOrEqualTo(Date value) {
            addCriterion("deliver >=", value, "deliver");
            return (Criteria) this;
        }

        public Criteria andDeliverLessThan(Date value) {
            addCriterion("deliver <", value, "deliver");
            return (Criteria) this;
        }

        public Criteria andDeliverLessThanOrEqualTo(Date value) {
            addCriterion("deliver <=", value, "deliver");
            return (Criteria) this;
        }

        public Criteria andDeliverIn(List<Date> values) {
            addCriterion("deliver in", values, "deliver");
            return (Criteria) this;
        }

        public Criteria andDeliverNotIn(List<Date> values) {
            addCriterion("deliver not in", values, "deliver");
            return (Criteria) this;
        }

        public Criteria andDeliverBetween(Date value1, Date value2) {
            addCriterion("deliver between", value1, value2, "deliver");
            return (Criteria) this;
        }

        public Criteria andDeliverNotBetween(Date value1, Date value2) {
            addCriterion("deliver not between", value1, value2, "deliver");
            return (Criteria) this;
        }

        public Criteria andHandoverIsNull() {
            addCriterion("handover is null");
            return (Criteria) this;
        }

        public Criteria andHandoverIsNotNull() {
            addCriterion("handover is not null");
            return (Criteria) this;
        }

        public Criteria andHandoverEqualTo(Date value) {
            addCriterion("handover =", value, "handover");
            return (Criteria) this;
        }

        public Criteria andHandoverNotEqualTo(Date value) {
            addCriterion("handover <>", value, "handover");
            return (Criteria) this;
        }

        public Criteria andHandoverGreaterThan(Date value) {
            addCriterion("handover >", value, "handover");
            return (Criteria) this;
        }

        public Criteria andHandoverGreaterThanOrEqualTo(Date value) {
            addCriterion("handover >=", value, "handover");
            return (Criteria) this;
        }

        public Criteria andHandoverLessThan(Date value) {
            addCriterion("handover <", value, "handover");
            return (Criteria) this;
        }

        public Criteria andHandoverLessThanOrEqualTo(Date value) {
            addCriterion("handover <=", value, "handover");
            return (Criteria) this;
        }

        public Criteria andHandoverIn(List<Date> values) {
            addCriterion("handover in", values, "handover");
            return (Criteria) this;
        }

        public Criteria andHandoverNotIn(List<Date> values) {
            addCriterion("handover not in", values, "handover");
            return (Criteria) this;
        }

        public Criteria andHandoverBetween(Date value1, Date value2) {
            addCriterion("handover between", value1, value2, "handover");
            return (Criteria) this;
        }

        public Criteria andHandoverNotBetween(Date value1, Date value2) {
            addCriterion("handover not between", value1, value2, "handover");
            return (Criteria) this;
        }

        public Criteria andBankidIsNull() {
            addCriterion("bankid is null");
            return (Criteria) this;
        }

        public Criteria andBankidIsNotNull() {
            addCriterion("bankid is not null");
            return (Criteria) this;
        }

        public Criteria andBankidEqualTo(String value) {
            addCriterion("bankid =", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidNotEqualTo(String value) {
            addCriterion("bankid <>", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidGreaterThan(String value) {
            addCriterion("bankid >", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidGreaterThanOrEqualTo(String value) {
            addCriterion("bankid >=", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidLessThan(String value) {
            addCriterion("bankid <", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidLessThanOrEqualTo(String value) {
            addCriterion("bankid <=", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidLike(String value) {
            addCriterion("bankid like", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidNotLike(String value) {
            addCriterion("bankid not like", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidIn(List<String> values) {
            addCriterion("bankid in", values, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidNotIn(List<String> values) {
            addCriterion("bankid not in", values, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidBetween(String value1, String value2) {
            addCriterion("bankid between", value1, value2, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidNotBetween(String value1, String value2) {
            addCriterion("bankid not between", value1, value2, "bankid");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}