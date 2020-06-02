#include <rpc/rpc.h>
#include "yp.h"

bool_t
xdr_ypstat(xdrs, objp)
	XDR *xdrs;
	ypstat *objp;
{
	if (!xdr_enum(xdrs, (enum_t *)objp)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypxfrstat(xdrs, objp)
	XDR *xdrs;
	ypxfrstat *objp;
{
	if (!xdr_enum(xdrs, (enum_t *)objp)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_domainname(xdrs, objp)
	XDR *xdrs;
	domainname *objp;
{
	if (!xdr_string(xdrs, objp, YPMAXDOMAIN)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_mapname(xdrs, objp)
	XDR *xdrs;
	mapname *objp;
{
	if (!xdr_string(xdrs, objp, YPMAXMAP)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_peername(xdrs, objp)
	XDR *xdrs;
	peername *objp;
{
	if (!xdr_string(xdrs, objp, YPMAXPEER)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_keydat(xdrs, objp)
	XDR *xdrs;
	keydat *objp;
{
	if (!xdr_bytes(xdrs, (char **)&objp->keydat_val, (u_int *)&objp->keydat_len, YPMAXRECORD)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_valdat(xdrs, objp)
	XDR *xdrs;
	valdat *objp;
{
	if (!xdr_bytes(xdrs, (char **)&objp->valdat_val, (u_int *)&objp->valdat_len, YPMAXRECORD)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypmap_parms(xdrs, objp)
	XDR *xdrs;
	ypmap_parms *objp;
{
	if (!xdr_domainname(xdrs, &objp->domain)) {
		return (FALSE);
	}
	if (!xdr_mapname(xdrs, &objp->map)) {
		return (FALSE);
	}
	if (!xdr_u_int(xdrs, &objp->ordernum)) {
		return (FALSE);
	}
	if (!xdr_peername(xdrs, &objp->peer)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypreq_key(xdrs, objp)
	XDR *xdrs;
	ypreq_key *objp;
{
	if (!xdr_domainname(xdrs, &objp->domain)) {
		return (FALSE);
	}
	if (!xdr_mapname(xdrs, &objp->map)) {
		return (FALSE);
	}
	if (!xdr_keydat(xdrs, &objp->key)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypreq_nokey(xdrs, objp)
	XDR *xdrs;
	ypreq_nokey *objp;
{
	if (!xdr_domainname(xdrs, &objp->domain)) {
		return (FALSE);
	}
	if (!xdr_mapname(xdrs, &objp->map)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypreq_xfr(xdrs, objp)
	XDR *xdrs;
	ypreq_xfr *objp;
{
	if (!xdr_ypmap_parms(xdrs, &objp->map_parms)) {
		return (FALSE);
	}
	if (!xdr_u_int(xdrs, &objp->transid)) {
		return (FALSE);
	}
	if (!xdr_u_int(xdrs, &objp->prog)) {
		return (FALSE);
	}
	if (!xdr_u_int(xdrs, &objp->port)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypresp_val(xdrs, objp)
	XDR *xdrs;
	ypresp_val *objp;
{
	if (!xdr_ypstat(xdrs, &objp->stat)) {
		return (FALSE);
	}
	if (!xdr_valdat(xdrs, &objp->val)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypresp_key_val(xdrs, objp)
	XDR *xdrs;
	ypresp_key_val *objp;
{
	if (!xdr_ypstat(xdrs, &objp->stat)) {
		return (FALSE);
	}
	if (!xdr_keydat(xdrs, &objp->key)) {
		return (FALSE);
	}
	if (!xdr_valdat(xdrs, &objp->val)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypresp_master(xdrs, objp)
	XDR *xdrs;
	ypresp_master *objp;
{
	if (!xdr_ypstat(xdrs, &objp->stat)) {
		return (FALSE);
	}
	if (!xdr_peername(xdrs, &objp->peer)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypresp_order(xdrs, objp)
	XDR *xdrs;
	ypresp_order *objp;
{
	if (!xdr_ypstat(xdrs, &objp->stat)) {
		return (FALSE);
	}
	if (!xdr_u_int(xdrs, &objp->ordernum)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypresp_all(xdrs, objp)
	XDR *xdrs;
	ypresp_all *objp;
{
	if (!xdr_bool(xdrs, &objp->more)) {
		return (FALSE);
	}
	switch (objp->more) {
	case TRUE:
		if (!xdr_ypresp_key_val(xdrs, &objp->ypresp_all_u.val)) {
			return (FALSE);
		}
		break;
	case FALSE:
		break;
	default:
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypresp_xfr(xdrs, objp)
	XDR *xdrs;
	ypresp_xfr *objp;
{
	if (!xdr_u_int(xdrs, &objp->transid)) {
		return (FALSE);
	}
	if (!xdr_ypxfrstat(xdrs, &objp->xfrstat)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypmaplist(xdrs, objp)
	XDR *xdrs;
	ypmaplist *objp;
{
	if (!xdr_mapname(xdrs, &objp->map)) {
		return (FALSE);
	}
	if (!xdr_pointer(xdrs, (char **)&objp->next, sizeof(ypmaplist), xdr_ypmaplist)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypresp_maplist(xdrs, objp)
	XDR *xdrs;
	ypresp_maplist *objp;
{
	if (!xdr_ypstat(xdrs, &objp->stat)) {
		return (FALSE);
	}
	if (!xdr_pointer(xdrs, (char **)&objp->maps, sizeof(ypmaplist), xdr_ypmaplist)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_yppush_status(xdrs, objp)
	XDR *xdrs;
	yppush_status *objp;
{
	if (!xdr_enum(xdrs, (enum_t *)objp)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_yppushresp_xfr(xdrs, objp)
	XDR *xdrs;
	yppushresp_xfr *objp;
{
	if (!xdr_u_int(xdrs, &objp->transid)) {
		return (FALSE);
	}
	if (!xdr_yppush_status(xdrs, &objp->status)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypbind_resptype(xdrs, objp)
	XDR *xdrs;
	ypbind_resptype *objp;
{
	if (!xdr_enum(xdrs, (enum_t *)objp)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypbind_binding(xdrs, objp)
	XDR *xdrs;
	ypbind_binding *objp;
{
	if (!xdr_opaque(xdrs, objp->ypbind_binding_addr, 4)) {
		return (FALSE);
	}
	if (!xdr_opaque(xdrs, objp->ypbind_binding_port, 2)) {
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypbind_resp(xdrs, objp)
	XDR *xdrs;
	ypbind_resp *objp;
{
	if (!xdr_ypbind_resptype(xdrs, &objp->ypbind_status)) {
		return (FALSE);
	}
	switch (objp->ypbind_status) {
	case YPBIND_FAIL_VAL:
		if (!xdr_u_int(xdrs, &objp->ypbind_resp_u.ypbind_error)) {
			return (FALSE);
		}
		break;
	case YPBIND_SUCC_VAL:
		if (!xdr_ypbind_binding(xdrs, &objp->ypbind_resp_u.ypbind_bindinfo)) {
			return (FALSE);
		}
		break;
	default:
		return (FALSE);
	}
	return (TRUE);
}

bool_t
xdr_ypbind_setdom(xdrs, objp)
	XDR *xdrs;
	ypbind_setdom *objp;
{
	if (!xdr_domainname(xdrs, &objp->ypsetdom_domain)) {
		return (FALSE);
	}
	if (!xdr_ypbind_binding(xdrs, &objp->ypsetdom_binding)) {
		return (FALSE);
	}
	if (!xdr_u_int(xdrs, &objp->ypsetdom_vers)) {
		return (FALSE);
	}
	return (TRUE);
}